package tactician.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.cards.other.Anathema;
import tactician.character.TacticianRobin;
import tactician.util.Wiz;
import static tactician.TacticianMod.makeID;

public class SecretBook extends TacticianRelic {
	private static final String NAME = "SecretBook";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.STARTER;
	private static final LandingSound SOUND = LandingSound.CLINK;
	private static final int FOCUS = 1;

	public SecretBook() {
		super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND);
		Anathema c = new Anathema();
		this.tips.add(new CardPowerTip(c));
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + FOCUS + this.DESCRIPTIONS[1]; }

	@Override
	public void playLandingSFX() { CardCrawlGame.sound.playV("tactician:LevelUpFE8", 0.95F); }

	@Override
	public void onEquip() { if (Wiz.isInCombat()) { this.counter = 1; }}

	@Override
	public void atBattleStart() { this.counter = 0; }

	@Override
	public void atTurnStart() {
		if (!this.grayscale) { this.counter++; }
		if (this.counter == 2) {
			flash();
			addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, FOCUS), FOCUS));
			addToTop(new MakeTempCardInHandAction(new Anathema(), 1));
			addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			this.counter = -1;
			this.grayscale = true;
		}
	}

	@Override
	public void onVictory() {
		this.counter = -1;
		this.grayscale = false;
	}

	@Override
	public AbstractRelic makeCopy() { return new SecretBook(); }
}