package tactician.effects.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import tactician.actions.PlaySoundAction;

public class BoltingEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private final int loops;

	public BoltingEffect(float x, float y, String soundKey, float volume, int loops) {
		this.x = x;
		this.y = y;
		this.soundKey = soundKey;
		this.volume = volume;
		this.loops = loops;
	}

	@Override
	public void update() {
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
		int i;
		for (i = 0; i < loops;) { AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(this.x, this.y))); i++; }
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}