package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import static basemod.helpers.CardModifierManager.copyModifiers;

public class TacticianMutateAction extends AbstractGameAction {
	private final AbstractCard mutatedCard;
	private final AbstractCard resultCard;
	private final boolean applyUpgrade;
	private final boolean effect;

	public TacticianMutateAction(AbstractCard mutatedCard, AbstractCard resultCard, boolean upgraded, boolean effect) {
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_XFAST;
		this.mutatedCard = mutatedCard;
		this.resultCard = resultCard;
		this.applyUpgrade = upgraded;
		this.effect = effect;
	}

	public void update() {
		boolean found = false;
		for (AbstractCard card : AbstractDungeon.player.hand.group) {
			if (card == this.mutatedCard) {
				found = true;
				break;
			}
		}
		if (found && this.mutatedCard != null) {
			copyModifiers(mutatedCard, resultCard, true, false, false);
			if (AbstractDungeon.player.hoveredCard == this.mutatedCard) { AbstractDungeon.player.releaseCard(); }
			AbstractDungeon.actionManager.cardQueue.removeIf(q -> (q.card == this.mutatedCard));
			if (this.effect) { AbstractDungeon.effectList.add(new PurgeCardEffect(mutatedCard)); } // Remove the old card from the hand.

			AbstractCard card = resultCard; // Add the new card to the hand.
			if (applyUpgrade) { card.upgrade(); }
			/*
			if (!AbstractDungeon.player.hasPower(ConfusionPower.POWER_ID)) { }
			// TODO: I cannot make the mutate account for Quick Burn / Corruption, Confusion, and Madness all at the same time.
			// TODO: One of these have to give, so I'm leaving the transformed card's cost at its default of 1 for now.
			 */
			addToBot(new MakeTempCardInHandAction(card, 1));
			AbstractDungeon.player.hand.removeCard(this.mutatedCard);
		}
		this.isDone = true;
	}
}