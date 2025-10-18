package tactician.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tactician.character.TacticianRobin;
import static tactician.TacticianMod.makeID;

public class FocusPotionT extends BasePotion {
    public static final String ID = makeID(FocusPotionT.class.getSimpleName());
    private static final Color LIQUID_COLOR = Color.BLUE;
    private static final Color HYBRID_COLOR = CardHelper.getColor(64, 64, 212);
    private static final Color SPOTS_COLOR = null; // CardHelper.getColor(255, 0, 255);

    public FocusPotionT() {
        super(ID, 2, PotionRarity.COMMON, PotionSize.S, AbstractPotion.PotionColor.SWIFT);
        playerClass = TacticianRobin.Meta.TACTICIAN;
        this.labOutlineColor = Settings.BLUE_RELIC_COLOR;
    }

    @Override
    public String getDescription() { return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1]; }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, potency), potency));
        }
    }

    @Override
    public void addAdditionalTips() { this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.FOCUS.NAMES[0]), GameDictionary.keywords.get(GameDictionary.FOCUS.NAMES[0]))); }
}