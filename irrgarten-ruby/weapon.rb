# encoding: utf-8

require_relative 'dice'

module Irrgarten
  class Weapon
    def initialize(power, uses)
      @power = power
      @uses = uses
    end

    def attack
      out = 0
      if @uses > 0
        out = @power
        @uses -= 1
      end
      out
    end

    def to_s
      "W[#{@power}, #{@uses}]"
    end

    def discard
      Dice.discard_element(@uses)
    end
  end
end