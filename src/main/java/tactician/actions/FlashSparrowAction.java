package tactician.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.effects.cards.FlashSparrowEffect;
import tactician.powers.weapons.*;
import tactician.util.Wiz;

public class FlashSparrowAction extends AbstractGameAction {
	private final DamageInfo info;
	private final AbstractMonster m;

	public FlashSparrowAction(AbstractPlayer p, AbstractMonster target, DamageInfo info) {
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.target = target;
		this.m = target;
		this.info = info;
	}

	public void update() {
		Color color;
		if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { color = Color.RED; }
		else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { color = Color.BLUE; }
		else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { color = Color.GREEN; }
		else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { color = Color.PINK; }
		else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { color = Color.CYAN; }
		else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { color = Color.ORANGE; }
		else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { color = Color.YELLOW; }
		else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { color = Color.PURPLE; }
		else { color = Color.LIGHT_GRAY; }

		addToTop(new VFXAction(new FlashSparrowEffect(target.hb.cX, target.hb.cY, "tactician:FlashSparrow", 1.33f, color)));
		addToTop(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.NONE));

		if ( Wiz.playerWeaponCalc(this.m, 9) > 0) {
			addToTop(new DrawCardAction(1));
			addToTop(new GainEnergyAction(1));
		}
		this.isDone = true;
	}
}