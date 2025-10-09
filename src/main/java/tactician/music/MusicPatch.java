package tactician.music;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*
public class MusicPatch {
	private static CustomMusic current;

	@SpirePatch(clz = MainMusic.class, method = "newBgm")
	public static class ReplaceMusic {
		@SpirePrefixPatch
		public static SpireReturn<Void> Prefix(MainMusic __instance, String key) {
			// Map StS "key" -> your music file
			if (key.equals("Exordium")) {
				current = new CustomMusic("audio/exordium.ogg", 10.5f, 97.2f);
				current.play();
				return SpireReturn.Return(null);
			}
			if (key.equals("TheCity")) {
				current = new CustomMusic("audio/city.ogg", 5.0f, 120.0f);
				current.play();
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = AbstractDungeon.class, method = "update")
	public static class UpdateMusic {
		@SpirePostfixPatch
		public static void Postfix() { if (current != null) { current.update(); } }
	}
} */