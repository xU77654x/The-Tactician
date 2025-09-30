package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.actions.PlaySoundAction;
import tactician.powers.KillingEdgePower;
import static tactician.TacticianMod.makeID;

public class KillingEdge extends BaseRelic {
	private static final String NAME = "KillingEdge";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.SHOP;
	private static final LandingSound SOUND = LandingSound.CLINK;
	private static final int COUNTER = 12;

	public KillingEdge() { super(ID, NAME, RARITY, SOUND); }

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

	@Override
	public void onEquip() { this.counter = 0; }

	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card.type == AbstractCard.CardType.ATTACK) {
			this.counter++;
			if (this.counter == COUNTER) {
				this.counter = 0;
				flash();
				this.pulse = false;
			}
			else if (this.counter == COUNTER - 1) {
				beginPulse();
				this.pulse = true;
				AbstractDungeon.player.hand.refreshHandLayout();
				addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KillingEdgePower(AbstractDungeon.player, 1), 1));
			}
		}
	}

	public void atBattleStart() {
		addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -1), -1));
		if (this.counter == COUNTER - 1) {
			beginPulse();
			this.pulse = true;
			AbstractDungeon.player.hand.refreshHandLayout();
			addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KillingEdgePower(AbstractDungeon.player, 1), 1));
		}
	}

	@Override
	public void playLandingSFX() { CardCrawlGame.sound.play("tactician:LevelUpFE8"); }

	@Override
	public AbstractRelic makeCopy() { return new KillingEdge(); }
}