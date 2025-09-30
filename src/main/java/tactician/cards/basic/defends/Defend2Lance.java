package tactician.cards.basic.defends;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.DefendSFXAction;
import tactician.cards.Tactician2LanceCard;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Defend2Lance extends Tactician2LanceCard {
	public static final String ID = makeID(Defend2Lance.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.BASIC,
			CardTarget.ENEMY,
			1
	);

	public Defend2Lance() {
		super(ID, info);
		setBlock(4, 3);
		tags.add(CardTags.STARTER_DEFEND);
		tags.add(CustomTags.LANCE);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon2LancePower.POWER_ID)) {addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); }
		calculateCardDamage(m);
		addToBot(new DefendSFXAction(m));
		addToBot(new GainBlockAction(p, p, this.block));
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		int realBlock = baseBlock;
		baseBlock += Wiz.playerWeaponCalc(m, 2);
		super.calculateCardDamage(m);
		baseBlock = realBlock;
		this.isBlockModified = (block != baseBlock);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Defend2Lance();
	}
}