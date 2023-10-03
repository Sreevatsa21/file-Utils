/**
 *	Describe the scorecard here.
 *
 *	@author	Sreevatsa Pervela
 *	@since	10/3/2023
 */
public class YahtzeeScoreCard {

	private int[] scorecard;
    private int[] count;
    
    public YahtzeeScoreCard() {
        this.scorecard = new int[14];
        for (int i = 0; i < this.scorecard.length; ++i) {
            this.scorecard[i] = -1;
        }
        this.count = new int[7];
    }
    /*public static void main(final String[] array) {
        final YahtzeeScoreCard yahtzeeScoreCard = new YahtzeeScoreCard();
        final DiceGroup diceGroup = new DiceGroup();
        diceGroup.rollDice();
        diceGroup.printDice();
        yahtzeeScoreCard.changeScore(1, diceGroup);
    }*/
	/**
	 *	Get a category score on the score card.
	 *	@param category		the category number (1 to 13)
	 *	@return				the score of that category
	 */
	public int getScore(i) {
		return this.scorecard[n];
	}
	
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	 public int getScore(final int n) {
        return this.scorecard[n];
    }
    
    public int getTotal() {
        int n = 0;
        for (int i = 1; i < 14; ++i) {
            n += this.scorecard[i];
        }
        return n;
    }
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) {
		int n2 = 0;
        for (int i = 0; i < 5; ++i) {
            final int lastRollValue = diceGroup.getDie(i).getLastRollValue();
            if (n == lastRollValue) {
                n2 += lastRollValue;
            }
        }
        this.scorecard[n] = n2;
	}
	
	public void countDie(final DiceGroup diceGroup) {
        for (int i = 1; i < 7; ++i) {
            this.count[i] = 0;
        }
        for (int j = 1; j < 6; ++j) {
            final int lastRollValue = diceGroup.getDie(j - 1).getLastRollValue();
            final int[] count = this.count;
            final int n = lastRollValue;
            ++count[n];
        }
    }
    
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg) {
			this.countDie(diceGroup);
        boolean b = false;
        for (int i = 1; i < 7; ++i) {
            if (this.count[i] > 2) {
                b = true;
            }
        }
        if (b) {
            this.scorecard[7] = diceGroup.getTotal();
        }
        else {
            this.scorecard[7] = 0;
        }
	}
	
	public void fourOfAKind(DiceGroup dg) {
		this.countDie(diceGroup);
        boolean b = false;
        for (int i = 1; i < 7; ++i) {
            if (this.count[i] > 3) {
                b = true;
            }
        }
        if (b) {
            this.scorecard[8] = diceGroup.getTotal();
        }
        else {
            this.scorecard[8] = 0;
        }	
	}
	
	public void fullHouse(DiceGroup dg) {
		 this.countDie(diceGroup);
        boolean b = false;
        boolean b2 = false;
        for (int i = 1; i < 7; ++i) {
            if (this.count[i] == 2) {
                b = true;
            }
            if (this.count[i] == 3) {
                b2 = true;
            }
        }
        if (b && b2) {
            this.scorecard[9] = 25;
        }
        else {
            this.scorecard[9] = 0;
        }
	}
	
	public void smallStraight(DiceGroup dg) {
		this.countDie(diceGroup);
        final boolean b = this.count[1] > 0 && this.count[2] > 0 && this.count[3] > 0 && this.count[4] > 0;
        final boolean b2 = this.count[2] > 0 && this.count[3] > 0 && this.count[4] > 0 && this.count[5] > 0;
        final boolean b3 = this.count[3] > 0 && this.count[4] > 0 && this.count[5] > 0 && this.count[6] > 0;
        if (b || b2 || b3) {
            this.scorecard[10] = 30;
        }
        else {
            this.scorecard[10] = 0;
        }
	}
	
	public void largeStraight(DiceGroup dg) {
		this.countDie(diceGroup);
        final boolean b = this.count[1] == 1 && this.count[2] == 1 && this.count[3] == 1 && this.count[4] == 1 && this.count[5] == 1;
        final boolean b2 = this.count[2] == 1 && this.count[3] == 1 && this.count[4] == 1 && this.count[5] == 1 && this.count[6] == 1;
        if (b || b2) {
            this.scorecard[11] = 40;
        }
        else {
            this.scorecard[11] = 0;
        }
	}
	
	public void chance(DiceGroup dg) {
		this.scorecard[12] = diceGroup.getTotal();
	}
	
	public void yahtzeeScore(DiceGroup dg) {
		this.countDie(diceGroup);
        boolean b = false;
        for (int i = 1; i < 7; ++i) {
            if (this.count[i] == 5) {
                b = true;
            }
        }
        if (b) {
            this.scorecard[13] = 50;
        }
        else {
            this.scorecard[13] = 0;
        }	
	}

}
