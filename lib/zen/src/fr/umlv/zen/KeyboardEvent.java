package fr.umlv.zen;

import java.util.Set;

import fr.umlv.zen.KeyboardKey.Modifier;

/**
 * A keyboard key and its set of modifiers.
 */
public class KeyboardEvent {
  private final KeyboardKey key;
  private final int modifiers;
  private /*lazy*/ Set<Modifier> modifierSet;

  KeyboardEvent(KeyboardKey key, int modifiers) {
    this.key = key;
    this.modifiers = modifiers;
  }
  
  /** Returns the keyboard key.
   * @return the keyboard key or {@link KeyboardKey#UNDEFINED} if the key is not recognized.
   */
  public KeyboardKey getKey() {
    return key;
  }
  
  /** A set of the modifier keys that can contain
   *  {@link KeyboardKey.Modifier#META}, {@link KeyboardKey.Modifier#CTRL}, {@link KeyboardKey.Modifier#ALT},
   *  {@link KeyboardKey.Modifier#SHIFT} and/or {@link KeyboardKey.Modifier#ALT_GR}. 
   * @return a set of modifier keys or an empty set if no modifier keys are used.
   */
  public Set<Modifier> getModifiers() {
    if (modifierSet != null)
      return modifierSet;
    return modifierSet = Modifier.modifierSet(modifiers);
  }
  
  @Override
  public String toString() {
    return getModifiers()+" "+getKey();
  }
}
