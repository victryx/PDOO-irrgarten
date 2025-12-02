# encoding: utf-8
require_relative 'shield'
require_relative 'weapon'

module Irrgarten
  class Player
    @@MAX_WEAPONS = 2
    @@MAX_SHIELDS = 3
    @@INITIAL_HEALTH = 10
    @@HITS2LOSE = 3

    def initialize(number, intelligence, strength)
      @name = "Player #" + number.to_s
      @number = number
      @intelligence = intelligence
      @strength = strength
      @health = @@INITIAL_HEALTH
      @consecutive_hits = 0
      set_pos(-1, -1)

      @weapons = []
      @shields = []
    end

    attr_reader :row, :col, :number # (getters)

    def resurrect
      @health = @@INITIAL_HEALTH
      reset_hits
      @weapons = []
      @shields = []
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def dead
      @health <= 0
    end

    def move(direction, valid_moves)
      if !valid_moves.include?(direction) && valid_moves.size > 0
        return valid_moves[0]
      end
      direction
    end

    def attack
      @strength + sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    def receive_reward
      wReward = Dice.weapons_reward
      sReward = Dice.shields_reward
      (0...wReward).each do
        receive_weapon(new_weapon)
      end
      (0...sReward).each do
        receive_shield(new_shield)
      end
      @health += Dice.health_reward
    end

    def to_s
      "#{@name.to_s}: {#{@intelligence.to_s}, #{@health.to_s}, #{@strength.to_s}}"
    end

    private

    def receive_weapon(w)
      @weapons.delete_if do |we|
        we.discard
      end

      if @weapons.size < @@MAX_WEAPONS
        @weapons.push(w)
      end
    end

    def receive_shield(s)
      @shields.delete_if do |sh|
        sh.discard
      end

      if @shields.size < @@MAX_SHIELDS
        @shields.push(s)
      end
    end

    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    def new_shield
      Shield.new(Dice.shield_power, Dice.uses_left)
    end

    def reset_hits
      @consecutive_hits = 0
    end

    def sum_weapons
      sum_weapons = 0
      @weapons.each do |weapon|
        sum_weapons += weapon.attack
      end
      sum_weapons
    end

    def sum_shields
      sum_shields = 0
      @shields.each do |shield|
        sum_shields += shield.protect
      end
      sum_shields
    end

    def defensive_energy
      @intelligence + self.sum_shields
    end

    def manage_hit(received_attack)
      defense = defensive_energy
      if defense < received_attack
        got_wounded
        inc_consecutive_hits
      else
        reset_hits
      end

      lose = @consecutive_hits == @@HITS2LOSE || dead
      if lose
        reset_hits
      end

      lose
    end

    def got_wounded
      @health -= 1
    end

    def inc_consecutive_hits
      @consecutive_hits += 1
    end

  end
end