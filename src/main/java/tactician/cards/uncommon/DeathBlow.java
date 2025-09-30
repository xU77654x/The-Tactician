package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeathBlowPower;
import tactician.util.CardStats;

public class DeathBlow extends TacticianCard {
	public static final String ID = makeID(DeathBlow.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.POWER,
			CardRarity.UNCOMMON,
			CardTarget.SELF,
			2
	);

	public DeathBlow() {
		super(ID, info);
		setMagic(3, 0);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new PlaySoundAction("tactician:StatIncreaseFE", 1.00f));
		addToBot(new ApplyPowerAction(p, p, new DeathBlowPower(this.magicNumber), this.magicNumber));
		if (this.upgraded) {
			addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
			addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new DeathBlow(); }
}