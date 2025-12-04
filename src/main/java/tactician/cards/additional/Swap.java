package tactician.cards.additional;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.actions.TacticianMutateAction;
import tactician.cards.Tactician9CopyCard;
import tactician.cards.basic.defends.*;
import tactician.cards.basic.strikes.*;
import tactician.character.TacticianRobin;
import tactician.effects.cards.SwapEffect;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;
import java.util.Objects;

public class Swap extends Tactician9CopyCard {
	public static final String ID = makeID(Swap.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.ATTACK,
			CardRarity.COMMON,
			CardTarget.ENEMY,
			1
	);

	public Swap() {
		super(ID, info);
		setDamage(7, 4);
		setSelfRetain(true);
		tags.add(CustomTags.COPY);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToTop(new PlaySoundAction("tactician:Swap", 1.75F));
		String sound = "tactician:Swap_Neutral";
		if ( Wiz.playerWeaponCalc(m, 9) > 0) { sound = "tactician:Swap_Strong"; }
		if ( Wiz.playerWeaponCalc(m, 9) < 0) { sound = "tactician:Swap_Weak"; }
		addToBot(new WaitAction(0.20F));
		addToBot(new VFXAction(new SwapEffect(m, m.hb.cX, m.hb.cY, sound, Wiz.getCopyColor())));
		addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

		for (AbstractCard card : p.hand.group) {
			boolean upgraded = card.upgraded;
			if (Objects.equals(card.cardID, Defend1Sword.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike1Sword(), upgraded, false)); }
			if (Objects.equals(card.cardID, Defend2Lance.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike2Lance(), upgraded, false)); }
			if (Objects.equals(card.cardID, Defend3Axe.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike3Axe(), upgraded, false)); }
			if (Objects.equals(card.cardID, Defend4Bow.ID)) { Wiz.atb(new TacticianMutateAction(card, new Strike4Bow(), upgraded, false)); }
			if (Objects.equals(card.cardID, Strike5Wind.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend5Wind(), upgraded, false)); }
			if (Objects.equals(card.cardID, Strike6Fire.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend6Fire(), upgraded, false)); }
			if (Objects.equals(card.cardID, Strike7Thunder.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend7Thunder(), upgraded, false)); }
			if (Objects.equals(card.cardID, Strike8Dark.ID)) { Wiz.atb(new TacticianMutateAction(card, new Defend8Dark(), upgraded, false)); }
		}
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
	public AbstractCard makeCopy() { return new Swap(); }
}