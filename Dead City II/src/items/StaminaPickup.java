package items;

import sprites.Creature;

public class StaminaPickup extends Pickup{

	

		private double staminaBoost;

		public StaminaPickup(double x, double y, double width, double height, double boostAmount) {
			super(x,y,width,height,"StaminaBoost");
			this.staminaBoost=boostAmount;
		}


		public void power(Creature target) {
			target.addStamina(staminaBoost);
		}







	}


