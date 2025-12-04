package tactician.patches.vanillapatches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.*;
import static tactician.character.TacticianRobin.Meta.TACTICIAN;

public class LoseStrIconPatch {
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/LoseStrengthCustom_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("LoseStrengthCustom.png"));

	@SpirePatch(clz = LoseStrengthPower.class, method = SpirePatch.CONSTRUCTOR)
	public static class replaceTexture {
		@SpirePostfixPatch
		public static void OverwriteLoseStrIcon(LoseStrengthPower _inst) {
			if (tempStatPatch || AbstractDungeon.player.chosenClass == TACTICIAN) {
				_inst.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 90, 90);
				_inst.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
			}
		}
	}
}