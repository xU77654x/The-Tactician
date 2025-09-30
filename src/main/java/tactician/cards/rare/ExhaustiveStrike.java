package tactician.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician3AxeCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.TacticianAxeEffect;
import tactician.powers.weapons.Weapon3AxePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class ExhaustiveStrike extends Tactician3AxeCard {
    public static final String ID = makeID(ExhaustiveStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public ExhaustiveStrike() {
        super(ID, info);
        setDamage(10, 3);
        setMagic(1, 1);
        tags.add(CardTags.STRIKE);
        tags.add(CustomTags.AXE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new PlayVoiceEffect("CA_Axe"));
        calculateCardDamage(m);
        addToBot(new VFXAction(new TacticianAxeEffect(m.hb.cX + (m.hb.width / 4.0F), m.hb.cY - (m.hb.height / 4.0F), "tactician:ExhaustiveStrike_Hit1", 1.25f, Color.OLIVE, 1.67F)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new WaitAction(0.67F));
        addToBot(new VFXAction(new TacticianAxeEffect(m.hb.cX + (m.hb.width / 4.0F), m.hb.cY - (m.hb.height / 4.0F), "tactician:ExhaustiveStrike_Hit2", 1.25f, Color.OLIVE, 1.67F)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        int exhaustAmount = magicNumber;
        if (Wiz.playerWeaponCalc(m, 9) > 0) { exhaustAmount += 3; }
        addToBot(new ExhaustAction(exhaustAmount, false, true, true));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); }
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
    public AbstractCard makeCopy() { return new ExhaustiveStrike(); }
}