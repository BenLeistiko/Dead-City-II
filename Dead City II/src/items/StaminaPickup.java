package items;

import gamePlay.Main;
import sprites.Creature;
import sprites.Hero;
/**
 * A pickup that gives a hero more stamina.
 * @author Jose
 * @version 5/21/18
 */
public class StaminaPickup extends Pickup{

	

		private double staminaBoost;

		public StaminaPickup(double x, double y, double boostAmount) {
			super(x,y,Main.resources.getImage("StaminaBoost").width,Main.resources.getImage("StaminaBoost").height,"StaminaBoost");
			this.staminaBoost=boostAmount;
		}


		public void power(Hero target) {
			target.addStamina(staminaBoost);
		}







	}


