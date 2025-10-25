# encoding: utf-8

module Irrgarten
  class Monster
    @@INITIAL_HEALTH = 5
    def initialize(name, intelligence, strength)
      @name = name
      @intelligence = intelligence
      @strength = strength
      @health = @@INITIAL_HEALTH
      set_pos(-1,-1)
    end

    def dead
      @health <= 0
    end

    def attack
      Dice.intensity(@strength)
    end

    def defend(received_attack)

    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def to_s
      "#{@name.to_s}: {#{@intelligence.to_s}, #{@health.to_s}, #{@strength.to_s}}"
    end

    private

    def got_wounded
      @health -= 1
    end

  end
end