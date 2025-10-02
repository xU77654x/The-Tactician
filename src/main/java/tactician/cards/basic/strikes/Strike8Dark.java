package tactician.cards.basic.strikes;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician8DarkCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.TacticianStrikeEffect;
import tactician.powers.weapons.Weapon8DarkPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Strike8Dark extends Tactician8DarkCard {
	public static final String ID = makeID(Strike8Dark.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.ATTACK,
			CardRarity.BASIC,
			CardTarget.ENEMY,
			1
	);

	public Strike8Dark() {
		super(ID, info);
		setDamage(5, 3);
		tags.add(CardTags.STARTER_STRIKE);
		tags.add(CardTags.STRIKE);
		tags.add(CustomTags.DARK);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon8DarkPower.POWER_ID)) {addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); }
		calculateCardDamage(m);
		addToBot(new VFXAction(new TacticianStrikeEffect(m, m.hb.cX, m.hb.cY, Color.PURPLE.cpy())));
		addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		int realDamage = baseDamage;
		baseDamage += Wiz.playerWeaponCalc(m, 8);
		super.calculateCardDamage(m);
		baseDamage = realDamage;
		this.isDamageModified = (damage != baseDamage);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Strike8Dark();
	}
}