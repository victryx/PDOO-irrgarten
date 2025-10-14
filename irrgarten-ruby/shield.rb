# encoding: utf-8

require_relative 'dice'

module Irrgarten

  class Shield
    def initialize(protection, uses)
      @protection = protection
      @uses = uses
    end

    def protect
      out = 0.0
      if @uses > 0
        out = @protection
        @uses -= 1
      end
      out
    end

    def discard
      Dice.discard_element(@uses)
    end

    def to_s
      "S[#{@protection}, #{@uses}]"
    end

  end

end

