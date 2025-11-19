package tactician.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tactician.character.TacticianRobin;
import tactician.powers.LoseFocusPower;
import static tactician.TacticianMod.makeID;

public class SagePotion extends TacticianPotion {
    public static final String ID = makeID(SagePotion.class.getSimpleName());
    private static final Color LIQUID_COLOR = Color.valueOf("0d429dff");
    private static final Color HYBRID_COLOR = CardHelper.getColor(64, 64, 212);
    private static final Color SPOTS_COLOR = null; // CardHelper.getColor(255, 0, 255);

    public SagePotion() {
        super(ID, 5, PotionRarity.COMMON, PotionSize.SNECKO, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        playerClass = TacticianRobin.Meta.TACTICIAN;
        this.labOutlineColor = new Color(Color.PURPLE);
    }

    @Override
    public String getDescription() { return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2]; }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, potency), potency));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseFocusPower(potency), potency));
        }
    }

    @Override
    public void addAdditionalTips() { this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.FOCUS.NAMES[0]), GameDictionary.keywords.get(GameDictionary.FOCUS.NAMES[0]))); }
}