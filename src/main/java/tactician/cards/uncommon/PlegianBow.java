package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.Weapon4Bow;
import tactician.cards.cardchoice.Weapon8Dark;
import tactician.character.TacticianRobin;
import tactician.effects.cards.TacticianBowEffect;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.powers.weapons.Weapon8DarkPower;
import tactician.util.CardStats;
import tactician.util.Wiz;

import java.util.ArrayList;

public class PlegianBow extends TacticianCard {
    public static final String ID = makeID(PlegianBow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    public int weapon;

    public PlegianBow() {
        super(ID, info);
        setDamage(14, 5);
        setMagic(3, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        weapon = 0;
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon4Bow(() ->  {
                weapon = 4;
                if (!p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }
                calculateCardDamage(m);
                addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:PlegianBow", 1.00f, Color.PINK.cpy())));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            }));
            easyCardList.add(new Weapon8Dark(() ->  {
                weapon = 8;
                if (!p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); }
                calculateCardDamage(m);
                addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:PlegianBow", 1.00f, Color.PURPLE.cpy())));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
        else {
            addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:PlegianBow", 1.00f, Color.PINK.cpy())));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        }
        addToBot(new ApplyPowerAction(m, p, new LockOnPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, weapon);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new PlegianBow(); }
}