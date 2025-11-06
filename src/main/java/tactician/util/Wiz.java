package tactician.util;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import tactician.character.TacticianRobin;
import tactician.powers.ZealPower;
import tactician.powers.weapons.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class Wiz {
    public static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }

    public static void atb(AbstractGameAction action) { AbstractDungeon.actionManager.addToBottom(action); }

    public static void att(AbstractGameAction action) { AbstractDungeon.actionManager.addToTop(action); }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        for (AbstractCard c : cardsList)
            consumer.accept(c);
    }

    public static int getLogicalPowerAmount(AbstractCreature ac, String powerId) {
        AbstractPower pow = ac.getPower(powerId);
        if (pow == null) { return 0; }
        return pow.amount;
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand) { masterCardsList.addAll(AbstractDungeon.player.hand.group); }
        if (includeExhaust) { masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group); }
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        for (AbstractMonster m : getEnemies()) { consumer.accept(m); }
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>((AbstractDungeon.getMonsters()).monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    private static boolean actuallyHovered(Hitbox hb) {
        return (InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y && InputHelper.mY < hb.y + hb.height);
    }

    public static boolean isInCombat() {
        return (CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }

    public static void topDeck(AbstractCard c, int i) { AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true)); }

    public static void topDeck(AbstractCard c) { topDeck(c, 1); }

    public static boolean isAttacking(AbstractCreature m) {
        if (m instanceof AbstractMonster) { return (((AbstractMonster)m).getIntentBaseDmg() >= 0); }
        return false;
    }

    public static AbstractGameAction.AttackEffect getRandomSlash() {
        int x = AbstractDungeon.miscRng.random(0, 2);
        if (x == 0) { return AbstractGameAction.AttackEffect.SLASH_DIAGONAL; }
        if (x == 1) { return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL; }
        return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
    }

    public static AbstractMonster getRandomEnemy() { return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng); }

    public static AbstractMonster getLowestHealthEnemy() {
        AbstractMonster weakest = null;
        for (AbstractMonster m : getEnemies()) {
            if (weakest == null) {
                weakest = m;
                continue;
            }
            if (weakest.currentHealth > m.currentHealth) { weakest = m; }
        }
        return weakest;
    }

    public static AbstractMonster getHighestHealthEnemy() {
        AbstractMonster strongest = null;
        for (AbstractMonster m : getEnemies()) {
            if (strongest == null) {
                strongest = m;
                continue;
            }
            if (strongest.currentHealth < m.currentHealth) { strongest = m; }
        }
        return strongest;
    }

    public static AbstractMonster getFrontmostEnemy() {
        AbstractMonster foe = null;
        float bestPos = 10000.0F;
        for (AbstractMonster m : getEnemies()) {
            if (m.drawX < bestPos) {
                foe = m;
                bestPos = m.drawX;
            }
        }
        return foe;
    }

    public static int pwrAmt(AbstractCreature check, String ID) {
        AbstractPower found = check.getPower(ID);
        if (found != null) { return found.amount; }
        return 0;
    }

    public static int getLogicalCardCost(AbstractCard c) {
        if (!c.freeToPlay()) {
            if (c.cost <= -2) { return 0; }
            if (c.cost == -1) { return EnergyPanel.totalCount; }
            return c.costForTurn;
        }
        return 0;
    }

    public static int countDebuffs(AbstractCreature c) {
        return (int)c.powers.stream().filter(pow -> (pow.type == AbstractPower.PowerType.DEBUFF)).count();
    }

    public static int countBuffs(AbstractCreature c) {
        return (int)c.powers.stream().filter(pow -> (pow.type == AbstractPower.PowerType.BUFF)).count();
    }

    public static int playerWeaponCalc (AbstractMonster m, int weaponType) {
        int valModify = 3;
        int effect = 0;
        boolean copyFlag = true;
        if (AbstractDungeon.player.hasPower(ZealPower.POWER_ID)) { effect = valModify; }
        else if (!(AbstractDungeon.player instanceof TacticianRobin)) { // TODO: This effect does not display calculateCardDamage correctly until one card later, where the effect doesn't apply.

            if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2) {
                if ((AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == AbstractCard.CardType.POWER) { effect = valModify; }
            }
            else { return 0; }
        }
        else if (m.hasPower(Weapon0NeutralPower.POWER_ID)) { return 0; }
        else do {
            switch (weaponType) {
                case 0: copyFlag = false; break;
                case 1:
                    if (m.hasPower(Weapon3AxePower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID)) { effect = valModify; }
                    else if (m.hasPower(Weapon2LancePower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID)) { effect = -valModify; }
                    copyFlag = false; break;
                case 2:
                    if (m.hasPower(Weapon1SwordPower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID)) { effect = valModify; }
                    else if (m.hasPower(Weapon3AxePower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID)) { effect = -valModify; }
                    copyFlag = false; break;
                case 3:
                    if (m.hasPower(Weapon2LancePower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID)) { effect = valModify; }
                    else if (m.hasPower(Weapon1SwordPower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID)) { effect = -valModify; }
                    copyFlag = false; break;
                case 4:
                    if (m.hasPower(Weapon1SwordPower.POWER_ID) || m.hasPower(Weapon2LancePower.POWER_ID) || (m.hasPower(Weapon3AxePower.POWER_ID))) { effect = valModify; }
                    else if (m.hasPower(Weapon5WindPower.POWER_ID) || m.hasPower(Weapon6FirePower.POWER_ID) || (m.hasPower(Weapon7ThunderPower.POWER_ID))) { effect = -valModify; }
                    copyFlag = false; break;
                case 5:
                    if (m.hasPower(Weapon7ThunderPower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID)) { effect = valModify; }
                    else if (m.hasPower(Weapon6FirePower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID)) { effect = -valModify; }
                    copyFlag = false; break;
                case 6:
                    if (m.hasPower(Weapon5WindPower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID)) { effect = valModify; }
                    else if (m.hasPower(Weapon7ThunderPower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID)) { effect = -valModify; }
                    copyFlag = false; break;
                case 7:
                    if (m.hasPower(Weapon6FirePower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID)) { effect = valModify; }
                    else if (m.hasPower(Weapon5WindPower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID)) { effect = -valModify; }
                    copyFlag = false; break;
                case 8:
                    if (m.hasPower(Weapon5WindPower.POWER_ID) || m.hasPower(Weapon6FirePower.POWER_ID) || (m.hasPower(Weapon7ThunderPower.POWER_ID))) { effect = valModify; }
                    else if (m.hasPower(Weapon1SwordPower.POWER_ID) || m.hasPower(Weapon2LancePower.POWER_ID) || (m.hasPower(Weapon3AxePower.POWER_ID))) { effect = -valModify; }
                    copyFlag = false; break;
                case 9:
                    if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { weaponType = 1; }
                    if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { weaponType = 2; }
                    if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { weaponType = 3; }
                    if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { weaponType = 4; }
                    if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { weaponType = 5; }
                    if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { weaponType = 6; }
                    if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { weaponType = 7; }
                    if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { weaponType = 8; }
                    if (!(AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID))) { weaponType = 0; }
                    if (!(m.hasPower(Weapon1SwordPower.POWER_ID) || m.hasPower(Weapon2LancePower.POWER_ID) || m.hasPower(Weapon3AxePower.POWER_ID) || m.hasPower(Weapon4BowPower.POWER_ID) || m.hasPower(Weapon5WindPower.POWER_ID) || m.hasPower(Weapon6FirePower.POWER_ID) || m.hasPower(Weapon7ThunderPower.POWER_ID) || m.hasPower(Weapon8DarkPower.POWER_ID))) { weaponType = 0; }
            }
        } while(copyFlag);
        return effect;
    }

    public static int savedWeapon = 0;

    public static void setNextCombatWeapon(int weapon) {
        savedWeapon = weapon;
        BaseMod.addSaveField("TacticianNextCombatWeapon", new CustomSavable<Integer>() {
            public Integer onSave() { return Wiz.savedWeapon; }
            public void onLoad(Integer weapon) { savedWeapon = weapon; }
        });

        SaveAndContinue.save(new SaveFile(SaveFile.SaveType.POST_COMBAT));
    }

    public static void applyNextCombatWeapon() {
        if (floorNum == 1) { savedWeapon = 0; } // TODO: Account for Downfall route's starting campfire.
        switch(savedWeapon) { // Required to do this nonsense due to SaveFile crashing from using AbstractPower instead of an integer.
            case 1: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon1SwordPower(AbstractDungeon.player))); break;
            case 2: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon2LancePower(AbstractDungeon.player))); break;
            case 3: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon3AxePower(AbstractDungeon.player))); break;
            case 4: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon4BowPower(AbstractDungeon.player))); break;
            case 5: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon5WindPower(AbstractDungeon.player))); break;
            case 6: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon6FirePower(AbstractDungeon.player))); break;
            case 7: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon7ThunderPower(AbstractDungeon.player))); break;
            case 8: case 0: Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon8DarkPower(AbstractDungeon.player))); break;
        }
        savedWeapon = 0;
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic", paramtypez = {})
    public static class OnStartOfCombatTactician {
        public static void Prefix(AbstractPlayer __instance) {
            if (AbstractDungeon.player instanceof TacticianRobin) { Wiz.applyNextCombatWeapon(); }
        }
    }
}