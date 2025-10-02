package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;
import tactician.util.CardStats;

import java.util.ArrayList;

public class Discipline extends TacticianCard {
    public static final String ID = makeID(Discipline.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Discipline() {
        super(ID, info);
        setBlock(6, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:Discipline", 1.25f));
        addToBot(new GainBlockAction(p, p, this.block));
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon1Sword(() -> { if (!p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); } }));
            easyCardList.add(new Weapon2Lance(() -> { if (!p.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); } }));
            easyCardList.add(new Weapon3Axe(() -> { if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); } }));
            easyCardList.add(new Weapon4Bow(() -> { if (!p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); } }));
            easyCardList.add(new Weapon5Wind(() -> { if (!p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); } }));
            easyCardList.add(new Weapon6Fire(() -> { if (!p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); } }));
            easyCardList.add(new Weapon7Thunder(() -> { if (!p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); } }));
            easyCardList.add(new Weapon8Dark(() -> { if (!p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); } }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Discipline(); }
}