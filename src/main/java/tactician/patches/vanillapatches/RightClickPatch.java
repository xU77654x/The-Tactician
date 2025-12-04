package tactician.patches.vanillapatches;

import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import tactician.actions.TacticianMutateAction;
import tactician.cards.basic.defends.*;
import tactician.cards.basic.strikes.*;
import tactician.util.CustomTags;
import tactician.util.Wiz;
import java.util.Objects;

public class RightClickPatch {

	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BasicRightClick");
	private static AbstractGameAction action;
	private static AbstractCard card;

	@SpirePatch(clz = AbstractCard.class, method = "update")
	public static class MutationFix {
		@SpirePostfixPatch
		public static void Postfix(AbstractCard __instance) {
			if (RightClickPatch.action != null && RightClickPatch.action.isDone) { RightClickPatch.action = null; }
			if (AbstractDungeon.player != null) {
				RightClickPatch.card = __instance;
				RightClickPatch.clickUpdate();
			}
		}
	}

	public static void clickUpdate() {
		if (!AbstractDungeon.isScreenUp && HitboxRightClick.rightClicked.get(card.hb) && !AbstractDungeon.actionManager.turnHasEnded) {
			onRightClick();
		}
	}

	public static void onRightClick() { if (RightClickPatch.action == null && card.hasTag(CustomTags.MUTATE)) {
		boolean upgraded = card.upgraded;
		if (Objects.equals(card.cardID, Strike1Sword.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend1Sword(), upgraded, false)); }
		if (Objects.equals(card.cardID, Strike2Lance.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend2Lance(), upgraded, false)); }
		if (Objects.equals(card.cardID, Strike3Axe.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend3Axe(), upgraded, false)); }
		if (Objects.equals(card.cardID, Strike4Bow.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend4Bow(), upgraded, false)); }
		if (Objects.equals(card.cardID, Defend5Wind.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike5Wind(), upgraded, false)); }
		if (Objects.equals(card.cardID, Defend6Fire.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike6Fire(), upgraded, false)); }
		if (Objects.equals(card.cardID, Defend7Thunder.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike7Thunder(), upgraded, false)); }
		if (Objects.equals(card.cardID, Defend8Dark.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike8Dark(), upgraded, false)); }
	}
	}
}
// Credit to Packmaster: Rip for the code related to right-click functionality for cards. Also, thanks to Enbeon and Ellie for help with the rest.