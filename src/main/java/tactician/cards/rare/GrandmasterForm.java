package tactician.cards.rare;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.GrandmasterFormPower;
import tactician.util.CardStats;

public class GrandmasterForm extends TacticianCard {
    public static final String ID = makeID(GrandmasterForm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public GrandmasterForm() {
        super(ID, info);
        setMagic(1, 0);
        setEthereal(true, false);
        tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new PlaySoundAction("tactician:GrandmasterForm", 0.40f)); // This stays as addToTop.
        addToBot(new VFXAction(new SanctityEffect(p.hb.cX, p.hb.cY)));
        if (this.upgraded) { addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1)); }
        addToBot(new ApplyPowerAction(p, p, new GrandmasterFormPower(this.magicNumber), this.magicNumber));
        AbstractDungeon.effectList.add(new PlayVoiceEffect("GrandmasterForm"));
    }

    @Override
    public AbstractCard makeCopy() { return new GrandmasterForm(); }
}