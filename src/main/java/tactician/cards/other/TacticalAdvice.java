package tactician.cards.other;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.*;
import tactician.effects.cards.fire.ArcfireBallEffect;
import tactician.powers.DeflectPower;
import tactician.powers.LoseFocusPower;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;
import java.util.ArrayList;
import java.util.List;

public class TacticalAdvice extends Tactician9CopyCard {
    public static final String ID = makeID(TacticalAdvice.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public TacticalAdvice() {
        super(ID, info);
        setDamage(0, 0);
        setBlock(0, 0);
        setMagic(0, 0);
        this.selfRetain = true;
        setExhaust(true);
        tags.add(CustomTags.COPY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { // Wrath Strike
            addToBot(new VFXAction(new PlayVoiceEffect("CA_Sword")));
            calculateCardDamage(m);
            addToBot(new VFXAction(new TacticianSwordLanceEffect(m.hb.cX, m.hb.cY, "tactician:WrathStrike", 1.33F, 195.0F, 0F, 0F, 3.5F, Color.SCARLET.cpy()), 0.00F));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { // Tempest Lance
            addToBot(new VFXAction(new PlayVoiceEffect("CA_Lance")));
            calculateCardDamage(m);
            addToBot(new VFXAction(new TacticianSwordLanceEffect(m.hb.cX, m.hb.cY, "tactician:TempestLance", 1.15F, 300F, 0F, 0F, 4.0F, Color.NAVY.cpy()), 0.00F));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new MakeTempCardInDrawPileAction(new Anathema(), 1, true, true));
        }
        else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { // Smash
            addToBot(new VFXAction(new PlayVoiceEffect("CA_Axe")));
            calculateCardDamage(m);
            addToBot(new VFXAction(new TacticianAxeEffect(m.hb.cX + (m.hb.width / 4.0F), m.hb.cY - (m.hb.height / 4.0F), "tactician:Smash", 1.01f, Color.CHARTREUSE.cpy(), 2.0F)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { // Curved Shot
            addToBot(new VFXAction(new PlayVoiceEffect("CA_Bow")));
            calculateCardDamage(m);
            addToBot(new GainBlockAction(p, this.block));
            addToBot(new VFXAction(new TacticianBowEffect(m.hb.cX, m.hb.cY, "tactician:CurvedShot", 1.25f, Color.BROWN.cpy())));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new PlaySoundAction("tactician:Strike_Neutral", 1.00f));
            if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && (AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount >= this.magicNumber)) {
                addToBot(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(DeflectPower.POWER_ID), this.magicNumber));
                addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), this.magicNumber));
            }
        }
        else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { // Elwind
            calculateCardDamage(m);
            addToBot(new VFXAction(new TacticianWindEffect(p.hb.cX, p.hb.cY, "tactician:Elwind", 1.50f, Color.CYAN.cpy(), 0.5F)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new VFXAction(new PlayVoiceEffect("Elwind")));
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { // Arcfire
            addToBot(new PlaySoundAction("tactician:Arcfire_Cast", 1.33f));
            addToBot(new VFXAction(new PlayVoiceEffect("Arcfire")));
            calculateCardDamage(m);
            addToBot(new VFXAction(p, new ArcfireBallEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.50F));
            addToBot(new PlaySoundAction("tactician:Arcfire_Hit", 1.33f));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { // Thunder
            calculateCardDamage(m);
            int loops = 2;
            if (this.upgraded) { loops += 1; }
            addToBot(new VFXAction(new PlayVoiceEffect("Thunder")));
            addToBot(new VFXAction(new BoltingEffect(m.hb.cX, m.hb.cY,"tactician:Thunder", 1.25f, loops)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new MakeTempCardInHandAction(new Hex(), 1));
            addToBot(new ExhaustAction(this.magicNumber, false, true, this.upgraded));
        }
        else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { // Flux
            addToBot(new PlaySoundAction("tactician:Flux", 1.00f));
            addToBot(new VFXAction(new PlayVoiceEffect("Flux")));
            calculateCardDamage(m);
            addToBot(new GainBlockAction(p, p, this.block));
            addToBot(new VFXAction(new TacticianDarkEffect(m.hb.cX, m.hb.cY, null, 1.00F, 6.67F, 0)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void updateContents(boolean forceReset) {
        this.tags.remove(CardTags.STRIKE);
        this.tags.remove(CustomTags.SWORD);
        this.tags.remove(CustomTags.LANCE);
        this.tags.remove(CustomTags.AXE);
        this.tags.remove(CustomTags.BOW);
        this.tags.remove(CustomTags.WIND);
        this.tags.remove(CustomTags.FIRE);
        this.tags.remove(CustomTags.THUNDER);
        this.tags.remove(CustomTags.DARK);
        this.tags.remove(CustomTags.COPY);
        this.cardsToPreview = null;
        setDamage(0, 0);
        setBlock(0, 0);
        setMagic(0, 0);
        if (!forceReset) {
            if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { // Wrath Strike
                if (this.upgraded) { setDamage(6); }
                else { setDamage(5); }
                if (this.upgraded) { setMagic(6); }
                else { setMagic(4); }
                this.tags.add(CustomTags.SWORD);
                this.tags.add(CardTags.STRIKE);
                this.name = cardStrings.EXTENDED_DESCRIPTION[14];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6];
                this.glowColor = Color.FIREBRICK;
            }
            else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { // Tempest Lance
                if (this.upgraded) { setDamage(16); }
                else { setDamage(12); }
                tags.add(CustomTags.LANCE);
                this.cardsToPreview = new Anathema();
                this.name = cardStrings.EXTENDED_DESCRIPTION[15];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[7];
                this.glowColor = Color.NAVY;
            }
            else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { // Smash
                if (this.upgraded) { setDamage(10); }
                else { setDamage(8); }
                if (this.upgraded) { setMagic(3); }
                else { setMagic(2); }
                tags.add(CustomTags.AXE);
                this.name = cardStrings.EXTENDED_DESCRIPTION[16];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[8];
                this.glowColor = Color.LIME;
            }
            else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { // Curved Shot
                if (this.upgraded) { setDamage(9); }
                else { setDamage(6); }
                setBlock(3, 0);
                if (this.upgraded) { setMagic(3); } // -1 !M! on upgrade.
                else { setMagic(4); }
                tags.add(CustomTags.BOW);
                this.name = cardStrings.EXTENDED_DESCRIPTION[17];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[9];
                this.glowColor = Color.MAROON;
            }
            else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { // Elwind
                if (this.upgraded) { setDamage(9); }
                else { setDamage(7); }
                if (this.upgraded) { setMagic(3); }
                else { setMagic(2); }
                tags.add(CustomTags.WIND);
                this.name = cardStrings.EXTENDED_DESCRIPTION[18];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[10];
                this.glowColor = Color.valueOf("40FF80"); // Too similar to Color.TEAL, but not much can be done about this.
            }
            else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { // Arcfire
                if (this.upgraded) { setDamage(10); }
                else { setDamage(8); }
                if (this.upgraded) { setMagic(3); }
                else { setMagic(2); }
                tags.add(CustomTags.FIRE);
                this.name = cardStrings.EXTENDED_DESCRIPTION[19];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[11];
                this.glowColor = Color.SCARLET;
            }
            else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { // Thunder
                if (this.upgraded) { setDamage(8); }
                else { setDamage(6); }
                if (this.upgraded) { setMagic(2); }
                else { setMagic(1); }
                tags.add(CustomTags.THUNDER);
                this.cardsToPreview = new Hex();
                this.name = cardStrings.EXTENDED_DESCRIPTION[20];
                if (this.upgraded) { this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[23]; }
                else { this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[12]; }

                this.glowColor = Color.YELLOW;
            }
            else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { // Flux
                if (this.upgraded) { setDamage(6); }
                else { setDamage(4); }
                if (this.upgraded) { setBlock(8); }
                else { setBlock(6); }
                tags.add(CustomTags.DARK);
                this.name =  cardStrings.EXTENDED_DESCRIPTION[21]; // Flux.class.getSimpleName();
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[13];
                this.glowColor = Color.PURPLE;
            }
            else {
                tags.add(CustomTags.COPY);
                this.name = cardStrings.NAME;
                this.rawDescription = cardStrings.DESCRIPTION;
                this.glowColor = Color.TEAL;
            }
        }
        else {
            tags.add(CustomTags.COPY);
            this.name = cardStrings.NAME;
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        if (this.upgraded) { upgradeName(); }
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() { updateContents(true); }

    @Override
    public void triggerOnExhaust() { updateContents(true); }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) { return false; }
        if (!(AbstractDungeon.player instanceof TacticianRobin)) { addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[22], 1.0F, 2.0F)); }
        if (!AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }

    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> toolTipList = new ArrayList<>();
        if (this.upgraded) { toolTipList.add(new TooltipInfo(cardStrings.NAME, cardStrings.EXTENDED_DESCRIPTION[2])); }
        else { toolTipList.add(new TooltipInfo(cardStrings.NAME, cardStrings.EXTENDED_DESCRIPTION[1])); }

        return toolTipList;
    }

    @Override
    public void applyPowers() {
        updateContents(false);
        int realDamage = baseDamage;
        if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID) && AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseDamage += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        updateContents(false);
        int realDamage = baseDamage;
        int realBlock = baseBlock;
        baseDamage += Wiz.playerWeaponCalc(m, 9);
        baseBlock += Wiz.playerWeaponCalc(m, 9);
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) {
            baseDamage += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        }
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        baseBlock = realBlock;
        this.isDamageModified = (damage != baseDamage);
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public AbstractCard makeCopy() { return new TacticalAdvice(); }

    // This card originally tried to copy from Rhythm Girl's Working Dough, but I changed the code to copy from Downfall: Gremlins' Gremlin Dance.
}