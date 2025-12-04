package tactician.cards.additional;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.WeakPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class DrawBack extends TacticianCard {
	public static final String ID = makeID(DrawBack.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.COMMON,
			CardTarget.SELF,
			0
	);

	public DrawBack() {
		super(ID, info);
		setMagic(2, -1);
		GraveField.grave.set(this, true);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// TODO: DrawBack VFX
		addToBot(new PlaySoundAction("tactician:DrawBack", 2.50F));
		if (!AbstractDungeon.player.orbs.isEmpty()) {
			for (AbstractOrb o : AbstractDungeon.player.orbs) {
				o.onStartOfTurn();
				o.onEndOfTurn();
			}
		}
		addToBot(new ApplyPowerAction(p, p, new WeakPower(p, this.magicNumber, false), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new DrawBack();
	}
}