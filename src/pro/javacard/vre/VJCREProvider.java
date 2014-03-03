package pro.javacard.vre;

import java.nio.ByteBuffer;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import javacard.framework.APDU;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactorySpi;

@SuppressWarnings("serial")
public class VJCREProvider extends Provider {
	public static final String PROVIDER_NAME = "VJCRE";
	public static final String TERMINAL_NAME = "VirtualJavaCard";

	public VJCREProvider() {
		super(PROVIDER_NAME, 0.0d, "Virtual JCRE from javacard.pro");
		put("TerminalFactory.PC/SC", VJCREProviderSpi.class.getName());
	}

	public static class VJCREProviderSpi extends TerminalFactorySpi {
		VRE vre = null;

		public VJCREProviderSpi(Object parameter) {
			if (parameter != null && (parameter instanceof VRE)) {
				vre = (VRE) parameter;
			}
		}

		@Override
		public CardTerminals engineTerminals() {
			return new JCTerminals(vre != null ? vre : VRE.getInstance());
		}


		private static class JCTerminals extends CardTerminals {
			final VRE vre ;
			protected JCTerminals(VRE vre) {
				this.vre = vre;
			}

			@Override
			public List<CardTerminal> list(State state) throws CardException {
				ArrayList<CardTerminal> terminals = new ArrayList<CardTerminal>();
				if (state == State.ALL || state == State.CARD_PRESENT) {
					terminals.add(new JCTerminal());
				}
				return terminals;
			}

			@Override
			public boolean waitForChange(long arg0) throws CardException {
				return false;
			}

			private class JCTerminal extends CardTerminal {
				@Override
				public Card connect(String protocol) throws CardException {
					if (protocol.equals("*") || protocol.equalsIgnoreCase("T=1")) {
						vre.connect(APDU.PROTOCOL_T1);
					} else if (protocol.equalsIgnoreCase("T=0")) {
						vre.connect(APDU.PROTOCOL_T0);
					} else {
						throw new IllegalArgumentException("Unknown protocol: " + protocol);
					}
					// FIXME: must re-use card objects
					return new JCCard();
				}

				@Override
				public String getName() {
					return TERMINAL_NAME;
				}

				@Override
				public boolean isCardPresent() throws CardException {
					return true;
				}

				@Override
				public boolean waitForCardAbsent(long arg0) throws CardException {
					return false;
				}

				@Override
				public boolean waitForCardPresent(long arg0) throws CardException {
					return true;
				}

				public class JCCard extends Card {
					private final CardChannel basicChannel;
					protected JCCard() {
						basicChannel = new JCCardChannel();
					}

					@Override
					public void beginExclusive() throws CardException {
						// TODO Auto-generated method stub
						// This makes no sense as there is just this JVM instance
						// Maybe synchronize on transmit?
					}

					@Override
					public void disconnect(boolean reset) throws CardException {
						// TODO: invalidate card?
						if (reset)
							vre.reset();
					}

					@Override
					public void endExclusive() throws CardException {
						// TODO Auto-generated method stub
					}

					@Override
					public ATR getATR() {
						// for OPSystem.setATRHistBytes()
						return vre.getATR();
					}

					@Override
					public CardChannel getBasicChannel() {
						return basicChannel;
					}

					@Override
					public String getProtocol() {
						if (vre.getProtocol() == APDU.PROTOCOL_T0)
							return "T=0";
						if (vre.getProtocol() == APDU.PROTOCOL_T1)
							return "T=1";
						throw new RuntimeException("vJCRE connection has unknown protocol!");
					}

					@Override
					public CardChannel openLogicalChannel() throws CardException {
						throw new CardException("Logical channels not supported");
					}

					@Override
					public byte[] transmitControlCommand(int arg0, byte[] arg1) throws CardException {
						throw new RuntimeException("Control commands don't make sense");
					}


					public class JCCardChannel extends  CardChannel {

						@Override
						public void close() throws CardException {
							// As only basic logical channel is supported
							throw new IllegalStateException("Can't close basic channel");
						}

						@Override
						public Card getCard() {
							return JCCard.this;
						}

						@Override
						public int getChannelNumber() {
							return 0;
						}

						@Override
						public ResponseAPDU transmit(CommandAPDU apdu) throws CardException {
							return vre.transmit(apdu);
						}

						@Override
						public int transmit(ByteBuffer arg0, ByteBuffer arg1) throws CardException {
							// TODO Auto-generated method stub
							return 0;
						}
					}
				}
			}
		}
	}
}

