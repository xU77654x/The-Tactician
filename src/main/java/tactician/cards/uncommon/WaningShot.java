package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician4BowCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.TacticianBowEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class WaningShot extends Tactician4BowCard {
    public static final String ID = makeID(WaningShot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public WaningShot() {
        super(ID, info);
        setDamage(7, 2);
        setMagic(1, 1);
        tags.add(CustomTags.BOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new PlaySoundAction("tactician:WaningShot_Draw", 1.25f)); // Robin does NOT make a voice with this card.
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }
        calculateCardDamage(m);
        addToBot(new WaitAction(0.50F));
        if (Settings.FAST_MODE) { addToBot(new WaitAction(1.00F)); }
        addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:CurvedShot", 1.25f, Color.SALMON.cpy())));
        addToBot(new PlaySoundAction("tactician:WaningShot_Hit", 1.25f));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && (AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount >= 4)) {
            addToBot(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(DeflectPower.POWER_ID), 4));
            if (!m.hasPower(ArtifactPower.POWER_ID)) { addToBot(new PlaySoundAction("tactician:StatDecreaseFE", 1.00f)); }
            addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -1), -1));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 4);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() {
        return new WaningShot();
    }
}