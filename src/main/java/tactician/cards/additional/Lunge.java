package tactician.cards.additional;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.FlashSparrowEffect;
import tactician.effects.cards.LungeEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Lunge extends Tactician9CopyCard {
	public static final String ID = makeID(Lunge.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			AbstractCard.CardType.ATTACK,
			AbstractCard.CardRarity.UNCOMMON,
			AbstractCard.CardTarget.ENEMY,
			0
	);

	public Lunge() {
		super(ID, info);
		setDamage(3, 0);
		setMagic(2, 1);
		setCustomVar("magicDeflect", 4, 4);
		tags.add(CustomTags.COPY);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new VFXAction(new LungeEffect(m.hb.cX, m.hb.cY, "tactician:Lunge", 1.40F, Wiz.getCopyColor())));
		addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
		if ( Wiz.playerWeaponCalc(m, 9) > 0) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DeflectPower(customVar("magicDeflect")), customVar("magicDeflect"))); }
		if ( Wiz.playerWeaponCalc(m, 9) < 0) { addToBot(new DrawCardAction(this.magicNumber)); }
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		if (m != null) {
			int realDamage = baseDamage;
			baseDamage += Wiz.playerWeaponCalc(m, 9);
			super.calculateCardDamage(m);
			baseDamage = realDamage;
			this.isDamageModified = (damage != baseDamage);
		}
	}

	@Override
	public AbstractCard makeCopy() { return new Lunge(); }
}