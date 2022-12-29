package prr;

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;

import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.DuplicateClientKeyCoreException;
import prr.exceptions.UnknownClientKeyCoreException;
import prr.exceptions.InvalidTerminalKeyCoreException;
import prr.exceptions.UnknownTerminalKeyCoreException;

import prr.Communications.Communication;
import prr.Communications.InteractiveCommunication;
import prr.Communications.TextCommunication;

import prr.Clients.Client;
import prr.Clients.NormalState;
import prr.Clients.GoldState;
import prr.Clients.PlatinumState;

import prr.terminals.Terminal;
import prr.terminals.FancyTerminal;
import prr.terminals.BasicTerminal;
import prr.terminals.IdleState;
import prr.terminals.OffState;
import prr.terminals.SilenceState;
import prr.terminals.BusyState;

/**
 * Network have clients, terminals and communications.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** Keep information about if network object has been changed. */
	private boolean _changed = false;

	/* Each client key as his client associated. */
	private Map<String, Client> _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);

	/* Each terminal has is ID associated. */
	private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>(String.CASE_INSENSITIVE_ORDER);

	/* Each communication has is ID associated. */
	private Map<Integer, Communication> _communications = new TreeMap<Integer, Communication>();

	/* Always keeps next communication number */
	private int _currentCommunicationNumber = 1;

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param fileName name of the text input file
	 * @throws UnrecognizedEntryException      if some entry is not correct
	 * @throws IOException                     if there is an IO error while
	 *                                         processing the text file
	 * @throws DuplicateClientKeyCoreException can be called from outside when
	 *                                         already exists an equal ID Client
	 * @throws UnknownClientKeyCoreException   can be called from outside when
	 *                                         there is not a client with that ID
	 */

	void importFile(String fileName) throws UnrecognizedEntryException, IOException,
			DuplicateClientKeyCoreException, UnknownClientKeyCoreException,
			InvalidTerminalKeyCoreException, UnknownTerminalKeyCoreException {
		try {
			String[] splited_line;
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				splited_line = line.split("\\|");
				try {
					RegisterObject(splited_line);
				} catch (UnrecognizedEntryException e1) {
					e1.printStackTrace();
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param changed
	 */
	public void setChanged(boolean changed) {
		_changed = changed;
	}

	/**
	 * @return _changed
	 */
	public boolean hasChanged() {
		return _changed;
	}

	/**
	 * Read the first line and check the type received.
	 * 
	 * @param splited_line line received
	 * @throws UnrecognizedEntryException      if some entry is not correct
	 * @throws DuplicateClientKeyCoreException can be called from outside when
	 *                                         already exists an equal ID Client
	 * @throws UnknownClientKeyCoreException   can be called from outside when
	 *                                         there is not a client with that ID
	 * @throws InvalidTerminalKeyCoreException can be called from outside when
	 *                                         terminal key is invalid
	 */

	public void RegisterObject(String[] splited_line) throws UnrecognizedEntryException,
			DuplicateClientKeyCoreException, UnknownClientKeyCoreException,
			InvalidTerminalKeyCoreException, UnknownTerminalKeyCoreException {
		switch (splited_line[0]) {
			case "CLIENT" -> RegisterClient(splited_line);
			case "BASIC", "FANCY" -> RegisterTerminal(splited_line);
			case "FRIENDS" -> AddFriendToTerminalNetwork(splited_line);
			default -> throw new UnrecognizedEntryException(splited_line[0]);
		}
	}

	/****************************** Clients Related ******************************/

	/**
	 * Read the text and register the client.
	 * 
	 * @param splited_line line received
	 * @throws UnrecognizedEntryException      if some entry is not correct
	 * @throws DuplicateClientKeyCoreException can be called from outside when
	 *                                         already exists an equal ID Client
	 */

	public void RegisterClient(String[] splited_line) throws UnrecognizedEntryException,
			DuplicateClientKeyCoreException {
		/* Zero's before non zero numbers in Client's taxID must be removed. */
		splited_line[3] = splited_line[3].replaceFirst("^0*", "");
		/* Verify entry fields. */
		if (!splited_line[0].equals("CLIENT") || splited_line[1].equals("") ||
				splited_line[2].equals("") || splited_line[3].equals(""))
			throw new UnrecognizedEntryException("Client");
		Client client = new Client(splited_line[1], splited_line[2], splited_line[3]);

		/* Add the new client to the others. */
		AddClient(splited_line[1], client);
	}

	/**
	 * Receive the key and client and add that client to the treemap of clients.
	 * 
	 * @param clientKey client ID
	 * @param client    Client
	 * @throws DuplicateClientKeyCoreException can be called from outside when
	 *                                         already exists an equal ID Client
	 */

	public void AddClient(String clientKey, Client client) throws DuplicateClientKeyCoreException {
		/* Verify if client's key already exists. */
		if (_clients.containsKey(clientKey)) {
			throw new DuplicateClientKeyCoreException(clientKey);
		}
		// If the key is new, add the client.
		else {
			_clients.put(clientKey, client);
			setChanged(true);
		}
	}

	/**
	 * Check if the client's key already exists
	 * 
	 * @param key client ID
	 * @return boolean (true or false)
	 */

	public boolean searchClient(String key) {
		return _clients.containsKey(key);

	}

	/**
	 * Get the client with the given id.
	 * 
	 * @param key the client's id.
	 * @return the client
	 */
	public Client getClient(String key) {
		return _clients.get(key);
	}

	/**
	 * @return all clients as an collection.
	 */
	public Collection getAllClients() {
		return _clients.values();
	}

	/**
	 * Check if the client's can receive notifications
	 * 
	 * @param client Client 
	 * @return boolean (true or false)
	 */

	public boolean CanReceiveNotifications(Client client) {
		return client.ClientNotifications();
	}

	/**
	 * Receive the client and assigns the notification to client
	 * 
	 * @param client    Client ID
	 * @param notification    notification
	 */

	public void setClientNotification(Client client, boolean notification) {
		client.setNotification(notification);
	}

	/**
	 * get the client debts with the given key
	 * 
	 * @param clientkey clientID 
	 * @return long, number of debts that client has
	 */

	public long getClientDebts(String clientKey) {
		Client client = getClient(clientKey);
		return client.getDebts();
	}

	/**
	 * get the client payments made with the given key
	 * 
	 * @param clientkey clientID 
	 * @return long, number of payments that client made
	 */

	public long getClientPayments(String clientKey) {
		Client client = _clients.get(clientKey);
		return client.getPayments();
	}

	/**
	 * create a list of clients, see _clients treeMap to check which clients 
	 * don´t have debts and get the list of clients whithout debts to pay
	 * 
	 * @return Collection clientsWithoutDebts, list of clients without debts
	 */

	public Collection getClientsWithoutDebts() {
		List<Client> clientsWithoutDebts = new ArrayList<Client>();
		for (Client client : _clients.values())
			if (client.getDebts() == 0)
				clientsWithoutDebts.add(client);
		
		return clientsWithoutDebts;
	}

	/**
	 * create a list of clients, see _clients treeMap to check which clients 
	 * have debts and get the list of clients with debts to pay
	 * 
	 * @return Collection clientsWithDebts, list of clients with debts
	 */

	public Collection getClientsWithDebts() {
		List<Client> clientsWithDebts = new ArrayList<Client>();
		for (Client client : _clients.values())
			if (client.getDebts() > 0)
				clientsWithDebts.add(client);
		
		Collections.sort(clientsWithDebts);
		return clientsWithDebts;
	}

	/**
	 * see the _clients TreeMap and check the payments of each client and sum to
	 * the totalPayments
	 * 
	 * @return long, number of all payments that clients made
	 */

	public long getAllPayments() {
		long totalPayments = 0;

		for (Client client : _clients.values()) {
			totalPayments += client.getPayments();
		}
		
		return totalPayments;
	}

	/**
	 * see the _clients TreeMap and check the debts of each client and sum to
	 * the totalDebts
	 * 
	 * @return long, number of all debts that clients have
	 */

	public long getAllDebts() {
		long totalDebts = 0;
		
		for (Client client : _clients.values()) {
			totalDebts += client.getDebts();
		}

		return totalDebts;
	}

	/**
	 * check the difference between de payments and debts of the Client given 
	 * the totalDebts
	 * 
	 * @param client Client
	 * @return long, difference between payments and debts - Balance
	 */

	public long getClientBalance(Client client) {
		long payments = client.getPayments();
		long debts = client.getDebts();

		return payments - debts;
	}

	
	/**
	 * get the list of client notifications
	 * 
	 * @param client Client
	 * @return List, list of client notifications
	 */
	public List getClientNotifications(Client client) {
		return client.getNotifications();
	}

	/**
	 * remove all the elements of the client notifications list
	 * 
	 * @param client Client
	 */
	public void clearClientNotifications(Client client) {
		client.ClearNotifications();
	}

	/*****************************************************************************/

	/***************************** Terminals Related *****************************/

	/**
	 * Read the text and register the terminal.
	 * 
	 * @param splited_line line received
	 * @throws UnrecognizedEntryException      if some entry is not correct
	 * @throws UnknownClientKeyCoreException   can be called from outside when
	 *                                         there is not a client with that ID
	 * @throws InvalidTerminalKeyCoreException can be called from outside when
	 *                                         terminal key is invalid
	 */
	public void RegisterTerminal(String[] splited_line) throws UnrecognizedEntryException,
			UnknownClientKeyCoreException, InvalidTerminalKeyCoreException {
		/* Some input verifications. */
		if ((!splited_line[0].equals("BASIC") && !splited_line[0].equals("FANCY"))
				|| splited_line[1].equals("") || splited_line[2].equals("") ||
				(!splited_line[3].equals("ON") && !splited_line[3].equals("OFF") &&
						!splited_line[3].equals("BUSY") && !splited_line[3].equals("SILENCE")))
			throw new UnrecognizedEntryException("Terminal");

		if (splited_line[0].equals("BASIC"))
			RegisterTerminalBasic(splited_line);
		if (splited_line[0].equals("FANCY"))
			RegisterTerminalFancy(splited_line);
	}

	/**
	 * Read the text and register the Fancy terminal.
	 * 
	 * @param splited_line line received
	 * @throws UnknownClientKeyCoreException   can be called from outside when
	 *                                         there is not a client with that ID
	 * @throws InvalidTerminalKeyCoreException can be called from outside when
	 *                                         terminal key is invalid
	 */
	public void RegisterTerminalFancy(String[] splited_line)
			throws UnknownClientKeyCoreException, InvalidTerminalKeyCoreException {
		Terminal terminal = new FancyTerminal(true, splited_line[1],
				splited_line[2], splited_line[3]);
		AddTerminal(splited_line[1], terminal);
	}

	/**
	 * Read the text and register the Basic terminal.
	 * 
	 * @param splited_line line received
	 * @throws UnknownClientKeyCoreException   can be called from outside when
	 *                                         there is not a client with that ID
	 * @throws InvalidTerminalKeyCoreException can be called from outside when
	 *                                         terminal key is invalid
	 */
	public void RegisterTerminalBasic(String[] splited_line)
			throws UnknownClientKeyCoreException, InvalidTerminalKeyCoreException {
		Terminal terminal = new BasicTerminal(false, splited_line[1],
				splited_line[2], splited_line[3]);
		AddTerminal(splited_line[1], terminal);
	}

	/**
	 * Receive the key and terminal and add that terminal to the treemap of terminals.
	 * 
	 * @param terminalID terminal ID
	 * @param terminal Terminal
	 * @throws UnknownClientKeyCoreException can be called from outside when 
	 * there is not a client with that ID
	 * @throws InvalidTerminalKeyCoreException can be called from outside when 
	 * terminal key is invalid
	 */
	public void AddTerminal(String terminalID, Terminal terminal) throws 
	UnknownClientKeyCoreException, InvalidTerminalKeyCoreException {
		_terminals.put(terminalID, terminal);
		/*Add this new terminal to client's number of terminals. */
		Client client = _clients.get(terminal.getClientID());
		if (!(_clients.containsKey(terminal.getClientID())))
			throw new UnknownClientKeyCoreException(terminal.getClientID());
		if (!terminal.getTerminalID().matches("(\\d{6})"))
			throw new InvalidTerminalKeyCoreException(terminal.getTerminalID());

		int numberOfTerminals = client.getClientNumberTerminals();
		numberOfTerminals = numberOfTerminals + 1;
		client.setClientNumberTerminals(numberOfTerminals);
		client.addTerminalToClient(terminalID, terminal);
		setChanged(true);
	}

	/**
	 * Reed the text and separate the terminals friends and the terminal, check 
	 * if terminals friends exists and add them to treeMap of terminals friends
	 * 
	 * @param splited_line text read
	 * @throws UnknownTerminalKeyCoreException can be called from outside when 
	 * there is not a client with that ID
	 */

	public void AddFriendToTerminalNetwork(String[] splited_line) throws UnknownTerminalKeyCoreException{

		String[] splited_line_friends;
		Terminal terminal = getTerminalNetwork(splited_line[1]);

		splited_line_friends = splited_line[2].split("\\,");
		int i = 0;
		while (i < splited_line_friends.length) {
			if(!searchTerminalNetwork(splited_line_friends[i]))
				throw new UnknownTerminalKeyCoreException(splited_line_friends[i]);
			else {
			Terminal terminalFriend = getTerminalNetwork(splited_line_friends[i]);
			terminal.addFriend(terminalFriend.getTerminalID(), terminalFriend);
			}
			i++;
		}

		setChanged(true);
	}

	/**
	 * Check if the terminal's key already exists
	 * 
	 * @param terminalID terminal ID
	 * @return boolean (true or false)
	 */
	public boolean searchTerminalNetwork(String terminalID) {
		return _terminals.containsKey(terminalID);
	}

	/**
	 * Get the terminal with the given id.
	 * 
	 * @param terminalKey the terminal's id.
	 * @return the terminal
	 */
	public Terminal getTerminalNetwork(String terminalKey) {
		return _terminals.get(terminalKey);
	}

	/**
	 * @return all terminals as an collection.
	 */
	public Collection getAllTerminals() {
		return _terminals.values();
	}

	/**
	 * @return all terminals as an collection that do not have any communications.
	 */
	public Collection getAllUnusedTerminals() {
		List<Terminal> unusedTerminals = new ArrayList<Terminal>();
		for (String terminalID : _terminals.keySet()) {
			Terminal terminal = _terminals.get(terminalID);
			if (terminal.getTerminalMadeCommunications().isEmpty()
					&& terminal.getTerminalReceivedCommunications().isEmpty())
				unusedTerminals.add(terminal);
		}
		return unusedTerminals;
	}

	/**
	 * create a list of terminals, see _terminals treeMap to check which terminals 
	 * have a positive balance and get the list of terminals with positive balance
	 * 
	 * @return terminalsWithPositiveBalance, list of terminals with 
	 * positive balance
	 */

	public Collection getTerminalsWithPositiveBalance() {
		List<Terminal> terminalsWithPositiveBalance = new ArrayList<Terminal>();
		for (Terminal terminal : _terminals.values())
			if ((terminal.getBalance()) > 0)
				terminalsWithPositiveBalance.add(terminal);
		
		return terminalsWithPositiveBalance;
	}

	/*****************************************************************************/

	/*************************** Communication Related ***************************/

	/**
	 * @return all communications as an collection.
	 */
	public Collection getAllCommunications() {
		return _communications.values();
	}

	/**
	 * increment to one the _currentCommunicationNumber variable that 
	 * corresponds to the communication key
	 */
	public void IncreaseCommunicationNumber() {
		_currentCommunicationNumber++;
	}

	/**
	 * @return int, _currentCommunicationNumber get the communication key
	 */

	public int getCommunicationNumber() {
		return _currentCommunicationNumber;
	}


	/**
	 * Get the communication Status 
	 * 
	 * @param terminal Terminal where the communication is
	 * @param communicationNumber communication key
	 * @return boolean (true or false), the communication status
	 */
	public boolean getStatusNetwork(Terminal terminal, int communicationNumber) {
		Communication communication = terminal.getCommunication(communicationNumber);

		return communication.getCommunicationStatus();
	}

	/**
	 * check if the communication was paid 
	 * 
	 * @param terminal Terminal where the communication is
	 * @param communicationNumber communication key
	 * @return boolean (true or false), status of the payment
	 */
	public boolean alreadyPaidNetwork(Terminal terminal, int communicationNumber) {
		Communication communication = terminal.getCommunication(communicationNumber);

		return communication.getCommunicationPaidStatus();
	}

	/**
	 * Get the communication with the given id.
	 * 
	 * @param key the communication's id.
	 * @return the client
	 */
	public Communication getCommunication(int key) {
		return _communications.get(key);
	}


	/**
	 * Check if the communication's key already exists
	 * 
	 * @param key communication ID
	 * @return boolean (true or false)
	 */

	public boolean searchCommunication(int key) {
		return _communications.containsKey(key);

	}

	/**
	 * Check if the communication's key already exists
	 * 
	 * @param key communication ID
	 * @return boolean (true or false)
	 */

	public boolean searchTerminalMadeCommunicationNetwork(Terminal terminal, int key) {
		if(terminal.searchMadeCommunication(key))
			return true;
		return false;

	}

	/**
	 * Receive the communication and add to the treemap of communications.
	 * 
	 * @param communication Communication
	 */

	public void AddCommunication(Communication communication) {
		_communications.put(_currentCommunicationNumber, communication);
		setChanged(true);
	}

	/**
	 * Receive the communication and the Terminal and add to the treemap of received communications.
	 * 
	 * @param terminal Terminal
	 * @param communication Communication
	 */
	
	public void AddReceivedCommunicationToTerminal(Terminal terminal, Communication communication) {
		terminal.AddToReceivedCommunicationsTerminal(communication, _currentCommunicationNumber);
		setChanged(true);
	}

	/**
	 * Receive the communication and the Terminal and add to the treemap of made
	 *  communications.
	 * 
	 * @param terminal Terminal
	 * @param communication Communication
	 */

	public void AddMadeCommunicationToTerminal(Terminal terminal, Communication communication) {
		terminal.AddToMadeCommunicationsTerminal(communication, _currentCommunicationNumber);
		setChanged(true);
	}


	/**
	 *  Create the interactive communication. Add the communication to the 
	 * respectives treeMaps. Check if it is a communication os the type Video 
	 * or Voice.
	 * 
	 * @param senderTerminal Terminal, terminal that send the interactive communication
	 * @param receiverTerminal Terminal, terminal that receive the interactive 
	 * communication
	 * @param communicationType String
	 * @param communication Communication
	 */
	public void StartInteractiveCommunicationNetwork(Terminal senderTerminal, Terminal receiverTerminal, String communicationTypeString) {
		Client client = _clients.get(senderTerminal.getClientID());
		int communicationType = 0;

		if(communicationTypeString.equals("VIDEO"))
			communicationType = 1;
		else 
			communicationType = 2;

		/*Create communication */
		Communication communication = new InteractiveCommunication(_currentCommunicationNumber,
				senderTerminal.getTerminalID(), receiverTerminal.getTerminalID(), communicationType);
				communication.setStatus(true);
				communication.setType(communicationType);
		
		/*Add the new to communication to both terminals. */
		AddMadeCommunicationToTerminal(senderTerminal, communication);
		AddReceivedCommunicationToTerminal(receiverTerminal, communication);
		AddCommunication(communication);

		/*Keep the previous terminal state and update teh new one. */
		senderTerminal.setPreviousState(senderTerminal.getTerminalState());
		senderTerminal.setTerminalState(new BusyState(senderTerminal));
		receiverTerminal.setPreviousState(receiverTerminal.getTerminalState());
		receiverTerminal.setTerminalState(new BusyState(receiverTerminal));

		if (communicationType == 1 && client.getClientType().toString().equals("GOLD")) {
			int consecutiveVideoCommunications = client.getNumberOfConsecutiveVideoCommunications();
			consecutiveVideoCommunications++;
			client.setNumberOfConsecutiveVideoCommunications(consecutiveVideoCommunications);
		}

		if (communicationType == 2)
			client.setNumberOfConsecutiveVideoCommunications(0);
		
		client.setNumberOfConsecutiveTextCommunications(0);

		/*Increase communication number to create the next communication. */
		IncreaseCommunicationNumber();

		setChanged(true);
	}


	/**
	 *  Get the ongoing communication and calculate the price.
	 * 
	 * @param terminal Terminal, terminal 
	 * @param communicationTime int, Communication time
	 * 
	 * @return long, the price of ended communication 
	 */

	public long EndInteractiveCommunicationNetwork(Terminal terminal, int communicationTime) {
		Client client = getClient(terminal.getClientID());

		/*Get the ongoing communication. */
		Communication communication = terminal.getOngoingCommunication();
		Terminal receiverTerminal = getTerminalNetwork(communication.getreceiverID());

		/*End the ongoing communication and define duration and price. */
		communication.setStatus(false);
		communication.setUnits(communicationTime);
		long communicationPrice = client.CalculateCommunicationPrice(communicationTime,
		communication.getType(), client.getTariffPlan());

		/*true if sender terminal is friends with receiver terminal. */
		if (terminal.hasDiscount(receiverTerminal))
			communicationPrice = communicationPrice/2;
		communication.setPrice(communicationPrice);
		client.addToDebts(communicationPrice);
		terminal.addToTerminalDebts(communicationPrice);

		/*Both terminals returns to previous state (before communication). */
		switch(terminal.getPreviouState().toString()) {
			case "IDLE" -> terminal.BusyToIdleTerminal();
			case "SILENCE" -> terminal.BusyToSilenceTerminal();
		}

		receiverTerminal.BusyToIdleTerminal();

		setChanged(true);

		/*Check if there is client type changes */
		long clientBalance = getClientBalance(client);
		if (clientBalance < 0 && (client.getClientType().toString().equals("PLATINUM")
		|| client.getClientType().toString().equals("GOLD"))) {
			client.setClientType(new NormalState(client));
			client.setNumberOfConsecutiveTextCommunications(0);
			client.setNumberOfConsecutiveVideoCommunications(0);
		}

		if (client.getNumberOfConsecutiveVideoCommunications() == 5 && clientBalance > 0
		&& client.getClientType().toString().equals("GOLD")) {
			client.setClientType(new PlatinumState(client));
			client.setNumberOfConsecutiveTextCommunications(0);
			client.setNumberOfConsecutiveVideoCommunications(0);
		}


		return communicationPrice;
	}

	/**
	 *  Create the text communication. Add the communication to the 
	 * respectives treeMaps.Calculate the price of the text Communication. 
	 * 
	 * @param senderTerminal Terminal, terminal that send the text communication
	 * @param receiverTerminal Terminal, terminal that receive the text 
	 * communication
	 * @param textMessage String
	 */

	public void SendTextCommunicationNetwork(Terminal senderTerminal, Terminal destinationTerminal, String textMessage) {
		Client client = _clients.get(senderTerminal.getClientID());

		/*Create communication */
		Communication communication = new TextCommunication(_currentCommunicationNumber,
		senderTerminal.getTerminalID(), destinationTerminal.getTerminalID());
        communication.setType(3);

		/*Add the new to communication to both terminals. */
        AddMadeCommunicationToTerminal(senderTerminal, communication);
        AddReceivedCommunicationToTerminal(destinationTerminal, communication);
        AddCommunication(communication);
		senderTerminal.setPreviousState(senderTerminal.getTerminalState());
		senderTerminal.setTerminalState(senderTerminal.getTerminalState());
		destinationTerminal.setPreviousState(destinationTerminal.getTerminalState());
		destinationTerminal.setTerminalState(destinationTerminal.getTerminalState());

		/*Calculate text communication price. */
		int communicationSize = textMessage.length();
		long communicationPrice = client.CalculateTextCommunicationPrice(communicationSize,
		communication.getType(), client.getTariffPlan());

		communication.setUnits(communicationSize);
		communication.setPrice(communicationPrice);
		client.addToDebts(communicationPrice);
		senderTerminal.addToTerminalDebts(communicationPrice);

		/*Check if there is client type changes */
		long clientBalance = getClientBalance(client);
		if (clientBalance < 0 && (client.getClientType().toString().equals("PLATINUM")
		|| client.getClientType().toString().equals("GOLD"))) {
			client.setClientType(new NormalState(client));
			client.setNumberOfConsecutiveTextCommunications(0);
			client.setNumberOfConsecutiveVideoCommunications(0);
		}

		/*if client type is platinum, increase the text message counter. */
		int consecutiveTextCommunications = client.getNumberOfConsecutiveTextCommunications();
		if (client.getClientType().toString().equals("PLATINUM")) {
			consecutiveTextCommunications++;
			client.setNumberOfConsecutiveTextCommunications(consecutiveTextCommunications);
		}
		/*consecutive video messages resets. */
		client.setNumberOfConsecutiveVideoCommunications(0);

		if (consecutiveTextCommunications == 2 && clientBalance > 0
		&& client.getClientType().toString().equals("PLATINUM")) {
			client.setClientType(new GoldState(client));
			client.setNumberOfConsecutiveTextCommunications(0);
			client.setNumberOfConsecutiveVideoCommunications(0);
		}

		/*Increase communication number to create the next communication. */
		IncreaseCommunicationNumber();

		setChanged(true);
	}


	/**
	 * create a list of communications and return the collection of the 
	 * communications from the client given
	 * 
	 * @param clientkey String
	 * 
	 * @return Collection clientCommunications, list of communications of the client
	 */

	public Collection getCommunicationsFromClient(String clientKey) {
		Client client = _clients.get(clientKey);
		Collection<Terminal> clientTerminals = client.getClientTerminals();
		List<Communication> clientCommunications = new ArrayList<Communication>();
		for (Terminal terminal : clientTerminals) {
			Collection<Communication> terminalCommunications = terminal.getTerminalMadeCommunications();
			clientCommunications.addAll(terminalCommunications);
		}

		/*Since this communication list gets the communications from each client,
		 * it could be unordered. */
		Collections.sort(clientCommunications);
		return clientCommunications;
	}


	/**
	 * create a list of communications and return the collection of the 
	 * communications that went send to the client given
	 * 
	 * @param clientkey String
	 * 
	 * @return Collection clientCommunications, list of communications that went
	 * send to the client
	 */

	public Collection getCommunicationsToClient (String clientKey) {
		Client client = _clients.get(clientKey);
		Collection<Terminal> clientTerminals = client.getClientTerminals();
		List<Communication> clientCommunications = new ArrayList<Communication>();
		for (Terminal terminal : clientTerminals) {
			Collection<Communication> terminalCommunications = terminal.getTerminalReceivedCommunications();
			clientCommunications.addAll(terminalCommunications);
		}

		/*Since this communication list gets the communications from each client,
		 * it could be unordered. */
		Collections.sort(clientCommunications);
		return clientCommunications;
	}


	/**
	 * pay the communication and check the change of client type
	 * 
	 * @param terminal Terminal
	 * @param communicationNumber int
	 * 
	 */

	public void payCommunicationNetwork(Terminal terminal, int communicationNumber) {

		Communication communication = terminal.getCommunication(communicationNumber);
		Client client = getClient(terminal.getClientID());

		client.removeDebts(communication.getPrice());
		terminal.removeFromTerminalDebts(communication.getPrice());
		client.addToPayments(communication.getPrice());
		terminal.addToTerminalPayments(communication.getPrice());
		communication.setPaidStatus(true);

		/*Check if there is client type changes */
		long clientBalance = getClientBalance(client);
		if (clientBalance > 500 && client.getClientType().toString().equals("NORMAL")) {
			client.setClientType(new GoldState(client));
			client.setNumberOfConsecutiveTextCommunications(0);
			client.setNumberOfConsecutiveVideoCommunications(0);
		}

		setChanged(true);

	}
	/*****************************************************************************/
}