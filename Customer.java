package sosaBank;

import dao.BankService;

public class Customer extends Human {
	

    //with the other methods all drawn out in the BANKSERVICE.deposit part you don't have to write them here
	//so you can just make a simple call and implement them


	public static void makeADeposit() throws Exception {
		
		System.out.println("How much CASH would you like to deposit?");
		int amountToDeposit = Human.freeInt(); // freeInt just allows user to input an amount
		if (amountToDeposit<= Game.cashOnHand) { //the amount you start the game with
			System.out.println("great!");
			BankService.deposit(getAccount(), amountToDeposit);//
		}else {
			System.out.println("you don't have that much CASH on you.");
			System.out.println("Annoyed you leave.");
			Game.leave();
		}
		System.out.println("...");
		Customer.moreWork();
	}

	public static void makeAWithdraw() throws Exception {

		System.out.println("How much CASH would you like to deposit?");
		int amountToDeposit = Human.freeInt();
		if (amountToDeposit<= Game.cashOnHand) {
			System.out.println("great!");
			BankService.withdraw(getAccount(), amountToDeposit);//
		}else {
			System.out.println("you don't have that much CASH on you.");
			System.out.println("Annoyed you leave.");
			Game.leave();
		}
		System.out.println("...");
		Customer.moreWork();
	}

	public static void checkAAccount() {
		int balance = BankService.lookUp(getAccount());
		System.out.println("this is your new balance  " + balance);
		Customer.moreWork();
	}

	public static void makeATransfer() {
		System.out.println("you need your account info + password, and the other person's information");
		Customer.moreWork();
	}
	
	public static void checkBalance() {
		int great = BankService.lookUp(getAccount());
		System.out.println("cool this is your balance " + great);
		Customer.moreWork();
	}

	public static void doWork() {

		System.out.println("Great, I'm YESSICA the MANAGER");
		System.out.println("What do you go by?");
		try {
			String anybody = Game.choice();
			if(anybody.equals("ROBBER")) {
				Robber.doWork();
			}
			if (anybody.equals("ROB")) {
				Robber.doWork();
			}
			setName(anybody);
		} catch (Exception e) {
			System.out.println("I just meant your name suga';");
			try {
				String anybody = Game.choice();
				setName(anybody);
			} catch (Exception e2) {
				System.out.println("Honey this just ain't yo thing';");
				Game.endGame();
			}
		}
		
		System.out.println("'Cool, we are a little short staffed today. Try the atm.'");
		System.out.println("You do and asked for your accountID");
		System.out.println("ACCOUNTID:");
		try { int whattheySaid = Human.freeInt();
		Human.setAccount(whattheySaid);
		System.out.println("PASSWORD:");
		String whatTheyWhispered = Human.freeWill();
		Human.setPassword(whatTheyWhispered);
		
		System.out.println("you wait hoping its you.");
		
		int validation = BankService.validateAccount(getAccount(),getPassword());
		if (validation == 3) {
			System.out.println("you're in");
			Customer.moreWork();
		} else {
			System.out.println("Invalid credentials, you might not have an ACCOUNT.");
			System.out.println("to create an ACCOUNT you need to see a TELLER");
			System.out.println("but now you're frustrated and you LEAVE");
			Game.leave();
		}
		} catch (Exception e) {
			System.out.println("I just meant your name suga';");
			try {
				String theSecret = Game.choice();
				setPassword(theSecret);
			} catch (Exception e2) {
				System.out.println("Honey this just ain't yo thing';");
				Game.endGame();
			}
		}
		
	

	}

	public static void moreWork() {

		// TODO Auto-generated method stub
		System.out.println("what would you like to do today?");
		System.out.println("make a DEPOSIT, WITHDRAW, or check ACCOUNT");
		try {
			String said = freeWill();
			if (said.equals("DEPOSIT")) {
				makeADeposit();
			} else if (said.equals("WITHDRAW")) {
				makeAWithdraw();
			} else if (said.equals("TRANSFER")) {
				makeATransfer();
			} else if (said.equals("ROB")) {
				Robber.doWork();
			} else if (said.equals("ROBBER")) {
				Robber.doWork();
			} else if (said.equals("ACCOUNT")) {
				checkAAccount();
			} else {

				System.out.println("That didn't work, remember you can always LEAVE. ");
				Customer.moreWork();
			}

		} catch (Exception e) {
			System.out.println("Customers can't do that much...try LEAVE");
			Customer.moreWork();
		}

	}





}
