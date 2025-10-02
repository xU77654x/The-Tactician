package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Reposition extends Tactician9CopyCard {
    public static final String ID = makeID(Reposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    public Reposition() {
        super(ID, info);
        setBlock(4, 3);
        setMagic(0, 0);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        addToBot(new PlaySoundAction("tactician:Reposition", 1.25f));
        addToBot(new GainBlockAction(p, p, this.block));
        if (p.hasPower(DexterityPower.POWER_ID)) {
            int dex = p.getPower(DexterityPower.POWER_ID).amount;
            if (dex > 0) { addToBot(new ApplyPowerAction(p, p, new DeflectPower(dex), dex)); }
        }
    }

    @Override
    public void applyPowers() {
        int realBlock = baseBlock;
        int realMagic = baseMagicNumber;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            baseBlock -= AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        super.applyPowers();
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBlock = baseBlock;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            baseBlock -= AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        baseBlock += Wiz.playerWeaponCalc(m, 9);
        super.calculateCardDamage(m);
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    // Strength, Focus, and Energy (if upgraded) grant additional Block. No longer used.
    /*
    @Override
    public void applyPowers() {
        int realBlock = baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) { baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount; }
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) { baseBlock += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount; }
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) { baseBlock += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount; }
        if (this.upgraded) { baseBlock += EnergyPanel.totalCount; }
        super.applyPowers();
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBlock = baseBlock;
        baseBlock += Wiz.playerWeaponCalc(m, 9);
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) { baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount; }
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) { baseBlock += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount; }
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) { baseBlock += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount; }
        if (this.upgraded) { baseBlock += EnergyPanel.totalCount; }
        super.calculateCardDamage(m);
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    } */

    @Override
    public AbstractCard makeCopy() { return new Reposition(); }
}