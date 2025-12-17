package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.Weapon3Axe;
import tactician.cards.cardchoice.Weapon5Wind;
import tactician.cards.other.Anathema;
import tactician.character.TacticianRobin;
import tactician.effects.cards.TacticianAxeEffect;
import tactician.powers.weapons.Weapon3AxePower;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.util.CardStats;
import tactician.util.Wiz;
import java.util.ArrayList;

public class HurricaneAxe extends TacticianCard {
    public static final String ID = makeID(HurricaneAxe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    public int weapon;

    public HurricaneAxe() {
        super(ID, info);
        setDamage(12, 5);
        setMagic(1, 0);
        this.cardsToPreview = new Anathema();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        weapon = 0;
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon3Axe(() ->  {
                weapon = 3;
                if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); }
                calculateCardDamage(m);
                addToBot(new VFXAction(new TacticianAxeEffect(m.hb.cX + (m.hb.width / 4.0F), m.hb.cY - (m.hb.height / 4.0F), "tactician:HurricaneAxe", 1.75f, Color.GREEN.cpy(), 1.875F)));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            }));
            easyCardList.add(new Weapon5Wind(() ->  {
                weapon = 5;
                if (!p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); }
                calculateCardDamage(m);
                addToBot(new VFXAction(new TacticianAxeEffect(m.hb.cX + (m.hb.width / 4.0F), m.hb.cY - (m.hb.height / 4.0F), "tactician:HurricaneAxe", 1.75f, Color.CYAN.cpy(), 1.875F)));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
        else {
            addToBot(new VFXAction(new TacticianAxeEffect(m.hb.cX + (m.hb.width / 4.0F), m.hb.cY - (m.hb.height / 4.0F), "tactician:HurricaneAxe", 1.75f, Color.GREEN.cpy(), 1.875F)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        }
        addToBot(new MakeTempCardInHandAction(new Anathema()));
        addToBot(new ExhaustAction(this.magicNumber, false));
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
    public AbstractCard makeCopy() {
        return new HurricaneAxe();
    }
}