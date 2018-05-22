package items;

import gamePlay.Main;
import sprites.Creature;

public class StaminaPickup extends Pickup{

	

		private double staminaBoost;

		public StaminaPickup(double x, double y, double boostAmount) {
			super(x,y,Main.resources.getImage("StaminaBoost").width,Main.resources.getImage("StaminaBoost").height,"StaminaBoost");
			this.staminaBoost=boostAmount;
		}


		public void power(Creature target) {
			target.addStamina(staminaBoost);
		}







	}

