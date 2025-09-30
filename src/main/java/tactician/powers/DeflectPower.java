package tactician.powers;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.actions.PlaySoundAction;
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
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            int deflect = this.amount;
            AbstractMonster m = (AbstractMonster)info.owner;
            int weaponStrong = max(0, Wiz.playerWeaponCalc(m, 9));
            if (weaponStrong == 0) {
                if (this.owner.hasPower(VantagePower.POWER_ID)) {
                    int acrobat = this.owner.getPower(VantagePower.POWER_ID).amount;
                    if (deflect > acrobat) { addToTop(new ReducePowerAction(this.owner, this.owner, this, deflect - acrobat)); }
                    addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
                else {
                    addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                    addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
            }
            else { // Ignore this error; IntelliJ is lying.
                if (this.owner.hasPower(VantagePower.POWER_ID)) {
                    int acrobat = this.owner.getPower(VantagePower.POWER_ID).amount;
                    if (deflect > acrobat) { addToTop(new ReducePowerAction(this.owner, this.owner, this, (deflect / 2) - acrobat)); }
                    addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
                else {
                    addToTop(new ReducePowerAction(this.owner, this.owner, this, (deflect / 2)));
                    addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
                addToTop(new WaitAction(0.1F));
                addToTop(new PlaySoundAction("tactician:DeflectReceiveHit", 1.25f));
            }
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }

    public AbstractPower makeCopy() { return new DeflectPower(this.amount); }
}