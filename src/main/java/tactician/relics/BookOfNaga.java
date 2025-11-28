package tactician.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.util.Wiz;
import static tactician.TacticianMod.makeID;

public class BookOfNaga extends TacticianRelic {
	private static final String NAME = "BookOfNaga";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.UNCOMMON;
	private static final LandingSound SOUND = LandingSound.CLINK;
	private static final int FOCUS = 1;

	public BookOfNaga() {
		super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND);
		Hex c = new Hex();
		this.tips.add(new CardPowerTip(c));
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + FOCUS + this.DESCRIPTIONS[1]; }

	@Override
	public void playLandingSFX() { CardCrawlGame.sound.playV("tactician:LevelUpFE8", 0.80F); }

	@Override
	public void onEquip() {
		if (AbstractDungeon.currMapNode == null) { this.counter = -1; }
		else {
			if (Wiz.isInCombatRelic()) { this.counter = 1; }
			else { this.counter = -1; }
		}
	}

	@Override
	public void atBattleStart() { this.counter = 0; }

	@Override
	public void atTurnStart() {
		if (!this.grayscale) { this.counter++; }
		if (this.counter == 2) {
			flash();
			addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, FOCUS), FOCUS));
			addToTop(new MakeTempCardInHandAction(new Hex(), 1));
			addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			this.counter = -1;
			this.grayscale = true;
		}
	}

	public void onVictory() {
		this.counter = -1;
		this.grayscale = false;
	}

	@Override
	public AbstractRelic makeCopy() { return new BookOfNaga(); }
}