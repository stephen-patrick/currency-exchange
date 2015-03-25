package currency.exchange.broker;

import java.util.ArrayList;
import java.util.List;

import play.libs.Akka;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import currency.exchange.models.FxMessage;

public final class ExchangeBrokerAkkaImpl implements ExchangeBroker {

	private static ActorRef defaultExchange = Akka.system().actorOf(
			Props.create(CurrencyExchange.class));

	@Override
	public void registerClient(ExchangeClient client) {
		defaultExchange.tell(new Join(client), null);

	}

	@Override
	public void unRegisterClient(ExchangeClient client) {
		defaultExchange.tell(new Leave(client), null);

	}

	@Override
	public void processMessage(FxMessage messageToProcess) {
		defaultExchange.tell(new FxRecieved(messageToProcess), null);
	}

	private static final class CurrencyExchange extends UntypedActor {

		private List<ExchangeClient> exchangeClientList = new ArrayList<ExchangeClient>();

		@Override
		public void onReceive(Object message) throws Exception {
			if (message == null) {
				return;
			}

			if (message instanceof FxRecieved) {
				for (ExchangeClient client : exchangeClientList) {
					client.handleMessage(((FxRecieved) message).message);
				}
			} else if (message instanceof Join) {
				Join joinMessage = (Join) message;

				if (!exchangeClientList.contains(joinMessage)) {
					exchangeClientList.add(joinMessage.client);
				}
			} else if (message instanceof Leave) {
				Leave leaveMessage = (Leave) message;
				exchangeClientList.remove(leaveMessage.client);
			}
		}

	}

	public static class Join {
		private ExchangeClient client;

		public Join(ExchangeClient client) {
			this.client = client;

		}
	}

	public static class Leave {
		private ExchangeClient client;

		public Leave(ExchangeClient client) {
			this.client = client;

		}
	}

	public static class FxRecieved {
		final FxMessage message;

		public FxRecieved(FxMessage message) {
			this.message = message;
		}
	}
}
