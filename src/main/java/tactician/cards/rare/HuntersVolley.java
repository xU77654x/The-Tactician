package tactician.cards.rare;

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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import tactician.cards.Tactician4BowCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.TacticianBowEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class HuntersVolley extends Tactician4BowCard {
    public static final String ID = makeID(HuntersVolley.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public HuntersVolley() {
        super(ID, info);
        setDamage(10, 4);
        setMagic(4, -3);
        tags.add(CustomTags.BOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new PlayVoiceEffect("CA_Bow"));
        calculateCardDamage(m);
        addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:HuntersVolley_Hit1", 1.00f, Color.SCARLET.cpy())));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new WaitAction(0.25F));
        addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:HuntersVolley_Hit2", 1.50f, Color.SCARLET.cpy())));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && (AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount >= this.magicNumber)) {
            addToBot(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(DeflectPower.POWER_ID), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, 2)));
        }
        // if (Wiz.playerWeaponCalc(m, 9) > 0) { addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, 1))); }
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }
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
    public AbstractCard makeCopy() { return new HuntersVolley(); }
}