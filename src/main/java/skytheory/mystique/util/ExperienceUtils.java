package skytheory.mystique.util;

public class ExperienceUtils {

	public static int getNeededXpTotal(int level) {
		return getNeededXpBetween(0, level);
	}
	
	public static int getNeededXpBetween(int min, int max) {
		int count = 0;
		for (int i = min; i < max; i++) {
			count += getNeededXpForNextLevel(i);
		}
		return count;
	}

	public static int getNeededXpForNextLevel(int level) {
		if (level >= 30) {
			return 112 + (level - 30) * 9;
		}
		if (level >= 15){
			return 37 + (level - 15) * 5;
		} else {
			return 7 + level * 2;
		}
	}

}
