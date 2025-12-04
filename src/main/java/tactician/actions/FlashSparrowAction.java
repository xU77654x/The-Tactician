package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.effects.cards.FlashSparrowEffect;
import tactician.util.Wiz;

public class FlashSparrowAction extends AbstractGameAction {
	private final DamageInfo info;
	private final AbstractMonster m;

	public FlashSparrowAction(AbstractPlayer p, AbstractMonster target, DamageInfo info) {
		this.actionType = ActionType.DAMAGE;
		this.target = target;
		this.m = target;
		this.info = info;
	}

	public void update() {
		if ( Wiz.playerWeaponCalc(this.m, 9) > 0) {
			addToTop(new DrawCardAction(1));
			addToTop(new GainEnergyAction(1));
		}
		addToTop(new DamageAction(this.target, this.info, AttackEffect.NONE));
		addToTop(new VFXAction(new FlashSparrowEffect(target.hb.cX, target.hb.cY, "tactician:FlashSparrow", 1.33f, Wiz.getCopyColor())));

		this.isDone = true;
	}
}