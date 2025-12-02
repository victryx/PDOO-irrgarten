# encoding: utf-8

require_relative 'directions'
require_relative 'game_character'
require_relative 'orientation'
require_relative 'weapon'
require_relative 'shield'
require_relative 'dice'
require_relative 'game_state'
require_relative 'labyrinth'
require_relative 'game'

module Irrgarten

  class TestP1
    def self.main
      w = Weapon.new(Dice.weapon_power, Dice.uses_left)
      puts w.to_s
      puts w.attack
      puts w.discard

      s = Shield.new(Dice.shield_power, Dice.uses_left)
      puts s.to_s
      puts s.protect
      puts s.discard

      g = GameState.new("Laberinto", "Jugadores", "Monstruos", 0, false, "log")
      puts g.labyrinth
      puts g.players
      puts g.monsters

      for i in 1..100 do
        puts "#{i}: #{Dice.random_strength}"
      end

      true_counter = 0
      for i in 1..100 do
        resurrects = Dice.resurrect_player
        puts "#{i}: #{resurrects}"
        if resurrects
          true_counter += 1;
        end
      end
      puts "Resucita #{true_counter}%\n"

      true_counter = 0
      for i in 1..100 do
        discarded = Dice.discard_element(3)
        # puts "#{i}: #{discarded}"
        if discarded
          true_counter += 1;
        end
      end
      puts "Descarta #{true_counter}%\n(Debería ser ~40%)\n"

      true_counter = 0
      for i in 1..100 do
        discarded = Dice.discard_element(5)
        # puts "#{i}: #{discarded}"
        if discarded
          true_counter += 1;
        end
      end
      puts "Descarta #{true_counter}%\n(Debería ser 0%)\n"

      true_counter = 0
      for i in 1..100 do
        discarded = Dice.discard_element(0)
        # puts "#{i}: #{discarded}"
        if discarded
          true_counter += 1;
        end
      end
      puts "Descarta #{true_counter}%\n(Debería ser 100%)\n"

      true_counter = 0
      for i in 1..100 do
        discarded = Dice.discard_element(1)
        # puts "#{i}: #{discarded}"
        if discarded
          true_counter += 1;
        end
      end
      puts "Descarta #{true_counter}%\n(Debería ser ~80%)\n"
    end
  end

  class TestP2
    def self.main
      l = Labyrinth.new(10, 10, 3, 4)
      puts l.to_s

      g = Game.new(13)

      puts g.game_state.labyrinth.to_s
    end
  end

end

Irrgarten::TestP2.main