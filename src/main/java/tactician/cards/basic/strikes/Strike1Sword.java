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
import tactician.cards.Tactician1SwordCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.TacticianStrikeEffect;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Strike1Sword extends Tactician1SwordCard {
	public static final String ID = makeID(Strike1Sword.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.ATTACK,
			CardRarity.BASIC,
			CardTarget.ENEMY,
			1
	);

	public Strike1Sword() {
		super(ID, info);
		setDamage(5, 3);
		tags.add(CardTags.STARTER_STRIKE);
		tags.add(CardTags.STRIKE);
		tags.add(CustomTags.SWORD);
		tags.add(CustomTags.MUTATE);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); }
		calculateCardDamage(m);
		addToBot(new VFXAction(new TacticianStrikeEffect(m, m.hb.cX, m.hb.cY, Color.RED)));
		addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
	}

	public void calculateCardDamage(AbstractMonster m) {
		int realDamage = baseDamage;
		baseDamage += Wiz.playerWeaponCalc(m, 1);
		super.calculateCardDamage(m);
		baseDamage = realDamage;
		this.isDamageModified = (damage != baseDamage);
	}

	@Override
	public AbstractCard makeCopy() { return new Strike1Sword(); }
}