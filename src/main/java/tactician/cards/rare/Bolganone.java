package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician6FireCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.fire.BolganoneEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Bolganone extends Tactician6FireCard {
    public static final String ID = makeID(Bolganone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Bolganone() {
        super(ID, info);
        setDamage(12, 2);
        setMagic(4, 1);
        tags.add(CustomTags.FIRE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Wiz.getCopyColor()));
        addToBot(new VFXAction(new PlayVoiceEffect("Bolganone")));
        calculateCardDamage(m);
        addToBot(new PlaySoundAction("tactician:Bolganone", 1.50f));
        addToBot(new VFXAction(new BolganoneEffect(m.hb.cX, m.hb.cY - 50), 0.0F));
        addToBot(new VFXAction(new InflameEffect(m), 0.25F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        int deflect = 0;
        if (Wiz.playerWeaponCalc(m, 9) > 0) {
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber)));
            deflect += this.magicNumber;
        }
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) { deflect += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount; }

        if (this.upgraded) { deflect += deflect; }
        if (deflect > 0) { addToBot(new ApplyPowerAction(p, p, new DeflectPower(deflect))); }
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 9);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Bolganone(); }
}