# encoding: utf-8

require_relative 'player'
require_relative 'labyrinth'
require_relative 'game'
require_relative 'text_ui'
require_relative 'controller'

module Irrgarten

  class TestP3
    include UI
    include Control

    def self.main
      p = Player.new(1,2,3)
      l = Labyrinth.new(10,10,5,6)

      l.spread_players([p])
      l.put_player(Directions::DOWN, p)

      puts l.to_s
      puts p.to_s

      g = Game.new(2)
      ui = TextUI.new
      con = Controller.new(g,ui)
      con.play
    end
  end

end


Irrgarten::TestP3.main