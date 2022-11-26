package main;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class GamePlay {

	private int rank = 0;

	/*
	 * method to start the game
	 * input- NA
	 * returns true/false
	 */
	public Player[] start()
	{
		Scanner sc = new Scanner(System.in);
		int numberOfPlayers = 0;

		Player[] players = null; //players instantiation

		try
		{	
			do
			{
				System.out.println("..........Game starts..........");
				System.out.println("Enter number of players");
				numberOfPlayers  = sc.nextInt();

			}
			while(! isValidNumberOfPlayers(numberOfPlayers));

			players = new Player[numberOfPlayers];
			int i = 0;
			while(i < numberOfPlayers)
			{
				players[i] = new Player(); //players initialization
				i++;
			}			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			return players;
		}


	}

	/*
	 * method to drive the game
	 */

	public void game(Player[] players)
	{
		while(! isGameEnded(players.length))
		{
			for(Player currentPlayer: players)
			{
				if(checkVictory(currentPlayer)) //if current player has won the game, skip to the next player
					continue;
				else
				{
					int diceValue; 
					Character selectedMarker;
					boolean conflict;
					do
					{
						conflict = false;
						System.out.println(currentPlayer.getPlayerName() + "'s turn");
						diceValue = currentPlayer.roll();
						System.out.println("Dice value " + diceValue);
						selectedMarker = currentPlayer.selectMarker();

						while(!(isValidMarker(currentPlayer, selectedMarker) && ! isMarkerAtHome(currentPlayer, selectedMarker)))
						{
							System.out.println("Invalid marker selected");
							selectedMarker = currentPlayer.selectMarker();
						}
						if(diceValue == 6) 
						{
							if(isMarkerAtJail(currentPlayer, selectedMarker))
								currentPlayer.move(selectedMarker, 1);
							else
							{
								if(! isHomeExceeded(currentPlayer, selectedMarker, diceValue))
								{
									currentPlayer.move(selectedMarker, diceValue);
									conflict = checkConflicts(currentPlayer, selectedMarker, players);
								}

							}

						}
						else //dice value can be 1/2/3/4/5
						{
							if((isMarkerAtJail(currentPlayer, selectedMarker) || isHomeExceeded(currentPlayer, selectedMarker, diceValue)))
								System.out.println("Invalid marker");
							else
							{
								currentPlayer.move(selectedMarker, diceValue);
								conflict = checkConflicts(currentPlayer, selectedMarker, players);
							}
						}
					}while(hasAnotherTurn(conflict, diceValue));
				}

				updateRank(currentPlayer); //update player's rank(if applicable) after every move
				System.out.println("Players's status");
				for(Player currPlayer: players)
				{
					System.out.println(currPlayer.getPlayerName() + " " + currPlayer.getMarkers());
				}
			}
		}

		endGame(players);
	}
	
	public boolean hasAnotherTurn(boolean conflict, Integer diceValue)
	{
		return conflict == true || diceValue == 6;
	}

	/*
	 * conflict satisfies if all 3 conditions are satisfied
	 * 1. otherPlayer's markerValue should not be in SAFE ZONE( >=51)
	 * 2. otherPLayer's markerValue should not be ZERO(just released from JAIL)
	 * 3. upon satisfying the above 2 conditions, if the difference between current and other markerValue when divided by 13 yields a non-zero
	 * then there is a conflict between 2 players,
	 * so, the otherPlayer's marker(which had a conflict with the current player) has to be sent to JAIL(-1)
	 */
	public boolean checkConflicts(Player currentPlayer, Character selectedMarker, Player[] players) {

		Integer currentMarkerValue = currentPlayer.getMarkers().get(selectedMarker);
		if(currentMarkerValue > 50) //no need to check for conflicts, if the current marker is in safe zone (51 and above)
			return false;
		for(Player otherPlayer: players)
		{
			if(otherPlayer.getPlayerName().equals(currentPlayer.getPlayerName())) //skip the current player(i.e) check conflicts only with other players
				continue;

			for(Map.Entry<Character, Integer> otherMarkerEntry : otherPlayer.getMarkers().entrySet())
			{
				
				if(!(isMarkerAtJail(otherPlayer, otherMarkerEntry.getKey())) && otherMarkerEntry.getValue() < 51 && otherMarkerEntry.getValue() != 0)
				{
					if((currentMarkerValue - otherMarkerEntry.getValue()) % 13 == 0 && isColorConflict(currentPlayer.getMarkerColor(), otherPlayer.getMarkerColor(), Math.abs(currentMarkerValue - otherMarkerEntry.getValue())))
					{
						arrest(otherPlayer, otherMarkerEntry.getKey());
						return true; //immediately return after making a conflict(avoids making multiple conflicts- if other player has 2 markers at the conflict zone, only one gets defeated at a time)
					}
					
				}
			}
		}
		
		return false;

	}

	/*
	 * method to check if the game has ended or not
	 */
	public boolean isGameEnded(int numberOfPlayers)
	{
		return rank == numberOfPlayers;
	}

	/*
	 * method to check if the user-entered player count is valid or not
	 * input - an integer representing the players' count
	 * returns TRUE if 2 <= numberOfPlayers <= 4 , FALSE  otherwise
	 */
	public boolean isValidNumberOfPlayers(int numberOfPlayers)
	{
		return numberOfPlayers >= 2 && numberOfPlayers <= 4;
	}

	/*
	 * method to end the game
	 */
	public void endGame(Player[] players)
	{
		System.out.println("..........Game over..........");
		System.out.println("Score Board");
		System.out.println("------------");
		System.out.println("RANK PLAYER NAME");
		Arrays.sort(players, (p1, p2) -> p1.getRank() - p2.getRank()); //sort players based on rank

		for(Player eachPlayer: players)
			System.out.println(eachPlayer.getRank() + "     " + eachPlayer.getPlayerName()); //displaying the score card

		System.out.println("*****************************************************************************************");//End of Game
	}


	public void updateRank(Player currentPlayer) 
	{
		if(checkVictory(currentPlayer))
			currentPlayer.setRank(++rank); 
	}

	/*
	 * method to check if a player has arrived HOME or not
	 * returns TRUE if all 4 markers are at HOME(56), FALSE otherwise
	 */
	public boolean checkVictory(Player p) 
	{
		for(Entry<Character, Integer> eachEntry: p.getMarkers().entrySet()) 
		{
			if(eachEntry.getValue() != 56)
				return false;
		}
		return true;

	}

	/*
	 * checks if the user entered character is valid or not.
	 * valid if the entered marker is either one among 'a', 'b', 'c', 'd' 
	 */
	public boolean isValidMarker(Player p, char c) 
	{
		return p.getMarkers().containsKey(c); 
	}
	
	public boolean isMarkerAtHome(Player p, Character selectedMarker) 
	{
		return p.getMarkers().get(selectedMarker) == 56;
	}

	public boolean isMarkerAtJail(Player p, Character selectedMarker) 
	{
		return p.getMarkers().get(selectedMarker) == -1 ;
	}
	
	/*
	 * method to check if a move can be made for a marker
	 * valid- if the marker upon displaced by the diceValue does not exceeds HOME(56)
	 * invalid- if the marker upon displaced by the diceValue exceeds HOME(56)
	 * returns TRUE if invalid, FALSE otherwise
	 */
	public boolean isHomeExceeded(Player p, char selectedMarker, int diceValue) 
	{
		return p.getMarkers().get(selectedMarker) + diceValue > 56;
	}
	
	/*
	 * The arrestedMarker for Player p will be moved to -1(JAIL)
	 */
	public void arrest(Player p, Character arrestedMarker)
	{
		p.move(arrestedMarker, -1*(p.getMarkers().get(arrestedMarker))-1);
	}
	
	public static boolean isColorConflict(Character currentColor, Character otherColor, Integer difference)
	{
		StringBuilder sb = new StringBuilder(currentColor + otherColor);
		String colorCombo = sb.toString();
		
		if(colorCombo.equals("rb") || colorCombo.equals("br") || colorCombo.equals("rg") || colorCombo.equals("gr"))
			return difference == 13 || difference == 39;
		else if(colorCombo.equals("ry") || colorCombo.equals("yr"))
			return difference == 26;
		else if(colorCombo.equals("by") || colorCombo.equals("yb"))
			return difference == 13 || difference == 39;
		else if(colorCombo.equals("bg") || colorCombo.equals("gb"))
			return difference == 26;
		else //colorCombo will be "gy" or "yg"
			return difference == 13 || difference == 39;
	}

}
