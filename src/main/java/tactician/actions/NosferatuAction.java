package tactician.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import tactician.effects.cards.TacticianDarkEffect;

public class NosferatuAction extends AbstractGameAction {
    private DamageInfo info;

    public NosferatuAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        tickDuration();
        if (this.isDone) {
            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) { addToTop(new AddTemporaryHPAction(this.source, this.source, this.target.lastDamageTaken)); }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) { AbstractDungeon.actionManager.clearPostCombatActions(); }
            else { addToTop(new WaitAction(0.1F)); }
            addToTop(new VFXAction(new TacticianDarkEffect(target.hb.cX, target.hb.cY, "tactician:Nosferatu", 2.50F, 2F, 10)));
        }
    }
}