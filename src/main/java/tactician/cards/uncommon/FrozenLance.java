package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import tactician.cards.Tactician2LanceCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.TacticianSwordLanceEffect;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class FrozenLance extends Tactician2LanceCard {
    public static final String ID = makeID(FrozenLance.class.getSimpleName());
    AbstractPlayer p;
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public FrozenLance() {
        super(ID, info);
        setDamage(7, 0);
        setBlock(2, 0);
        setMagic(3, 2);
        this.p = AbstractDungeon.player;
        tags.add(CustomTags.LANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PlayVoiceEffect("CA_Lance")));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); }
        calculateCardDamage(m);
        int dex = 0;
        if (this.p.hasPower(DexterityPower.POWER_ID)) { dex = (this.p.getPower(DexterityPower.POWER_ID)).amount; }
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new VFXAction(new BlizzardEffect(((dex * 2) + 3), AbstractDungeon.getMonsters().shouldFlipVfx()), 0.2F));
        addToBot(new VFXAction(new TacticianSwordLanceEffect(m.hb.cX, m.hb.cY, "tactician:FrozenLance", 1.15F, 255F, 0F, 0F, 3.0F, Color.CYAN.cpy()), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void applyPowers() {
        int dex = 0;
        if (this.p.hasPower(DexterityPower.POWER_ID)) { dex = (this.p.getPower(DexterityPower.POWER_ID)).amount; }
        int realDamage = this.baseDamage;
        int realBlock = this.baseBlock;
        this.baseDamage += (this.magicNumber * dex);
        super.applyPowers();
        this.baseDamage = realDamage;
        this.baseBlock = realBlock;
        this.isDamageModified = (this.damage != this.baseDamage);
        this.isBlockModified = (this.block != this.baseBlock);
    }

    public void calculateCardDamage(AbstractMonster m) {
        int dex = 0;
        if (this.p.hasPower(DexterityPower.POWER_ID)) { dex = (this.p.getPower(DexterityPower.POWER_ID)).amount; }
        int realDamage = this.baseDamage;
        int realBlock = this.baseBlock;
        baseDamage += Wiz.playerWeaponCalc(m, 2);
        baseBlock += Wiz.playerWeaponCalc(m, 2);
        this.baseDamage += (this.magicNumber * dex);
        super.calculateCardDamage(m);
        this.baseDamage = realDamage;
        this.baseBlock = realBlock;
        this.isDamageModified = (this.damage != this.baseDamage);
        this.isBlockModified = (this.block != this.baseBlock);
    }

    /*
    This can be used to factor Strength and Focus together.
    @Override
    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null)  { strength.amount *= this.magicNumber; }
        int focus = 0;
        if (this.p.hasPower(FocusPower.POWER_ID)) { focus = (this.p.getPower(FocusPower.POWER_ID)).amount; }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += (this.magicNumber * focus);
        super.applyPowers();
        if (strength != null) { strength.amount /= this.magicNumber; }
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster m) {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null)  { strength.amount *= this.magicNumber; }
        int focus = 0;
        if (this.p.hasPower(FocusPower.POWER_ID)) { focus = (this.p.getPower(FocusPower.POWER_ID)).amount; }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += (this.magicNumber * focus);
        super.calculateCardDamage(m);
        if (strength != null) { strength.amount /= this.magicNumber; }
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }
     */

    @Override
    public AbstractCard makeCopy() { return new FrozenLance(); }
}