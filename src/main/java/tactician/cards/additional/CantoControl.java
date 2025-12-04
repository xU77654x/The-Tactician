package tactician.cards.additional;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.CantoControlPower;
import tactician.util.CardStats;

public class CantoControl extends TacticianCard {
	public static final String ID = makeID(CantoControl.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.POWER,
			CardRarity.UNCOMMON,
			CardTarget.SELF,
			1
	);

	public CantoControl() {
		super(ID, info);
		setMagic(1, 0);
		setInnate(false, true);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new PlaySoundAction("tactician:CantoControl", 1.33F));
		// TODO: CantoControl VFX
		addToBot(new ApplyPowerAction(p, p, new CantoControlPower(this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new CantoControl();
	}
}