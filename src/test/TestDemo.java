package test;

public class TestDemo {

	public static void main(String[] args) {

//		testPlayer();
		testGamePlay();
	}

	public static void testPlayer()
	{
		TestPlayer tp = new TestPlayer();

		tp.testPlayerInit();
		tp.testRoll();
		tp.testMove();
	}
	
	public static void testGamePlay()
	{
		TestGamePlay tgp = new TestGamePlay();
		
//		tgp.testPlayerCount();
//		tgp.testEndGame();
//		tgp.testIsHomeExceeded();
//		tgp.testIsMarkerAtJail();
//		tgp.testIsMarkerAtHome();
//		tgp.testIsValidMarker();
//		tgp.testCheckVictory();
//		tgp.testUpdateRank();
//		tgp.testIsGameEnded();
//		tgp.testCheckConflicts_1();
//		tgp.testCheckConflicts_2();
		//tgp.testCheckConflicts_3();
		tgp.testCheckConflicts_4();
//		tgp.testHasAnotherTurn();
	}

}
