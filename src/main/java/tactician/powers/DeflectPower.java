package tactician.powers;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import tactician.TacticianMod;
import tactician.actions.PlaySoundAction;
import tactician.character.TacticianRobin;
import tactician.util.TextureLoader;
import tactician.util.Wiz;
import static java.lang.Math.max;
import static tactician.TacticianMod.powerPath;

public class DeflectPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TacticianMod.makeID("DeflectPower");
    private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/Deflect_Large.png"));
    private static final Texture tex32 = TextureLoader.getTexture(powerPath("Deflect.png"));
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DeflectPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (AbstractDungeon.player.chosenClass == null || AbstractDungeon.player instanceof TacticianRobin) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
        else { this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]; }
    }

    @Override
    public void playApplyPowerSfx() { }

    @Override
    public void onInitialApplication() { addToTop(new PlaySoundAction("tactician:DeflectGain", 1.67F)); }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        addToTop(new PlaySoundAction("tactician:DeflectGain", 1.67F));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            int deflect = this.amount;
            AbstractMonster m = (AbstractMonster)info.owner;
            int weaponStrong = max(0, Wiz.playerWeaponCalc(m, 9));
            if (weaponStrong == 0) {
                if (this.owner.hasPower(SolPower.POWER_ID)) { addToTop(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, this.amount), this.amount)); }
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                if (this.owner.hasPower(VantagePower.POWER_ID)) { addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true)); }
                else { addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true)); }
            }
            else {
                if (this.owner.hasPower(SolPower.POWER_ID)) { addToTop(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, this.amount), deflect / 2)); }
                addToTop(new ReducePowerAction(this.owner, this.owner, this, (deflect / 2)));
                if (this.owner.hasPower(VantagePower.POWER_ID)) { addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true)); }
                else { addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true)); }
                addToTop(new WaitAction(0.1F));
                addToTop(new PlaySoundAction("tactician:DeflectReceiveHit", 1.25f));
            }
        }
        return damageAmount;
    }

    public AbstractPower makeCopy() { return new DeflectPower(this.amount); }
}