package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.Figure._
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class FigureTest extends WordSpec with Matchers{
  "A Figure" when {
    "new Scout" should {
      val player = Player("name", FieldType.B_SIDE)
      val figure = Scout(player)
      "have a FigureTyp" in {
        figure.figureType should be (FigureType.SCOUT)
      }
      "have a strength" in {
        figure.strength should be (2)
      }
      "have a String for Tui" in {
        figure.stringOut should be ("[02]")
      }
      "have a String representation" in {
        figure.toString should be ("[02]")
      }
      "have a player" in {
        figure.player should be (player)
      }
    }
    "new Marshal" should {
      val player = Player("name", FieldType.B_SIDE)
      val figure = Marshal(player)
      "have a FigureTyp" in {
        figure.figureType should be (FigureType.MARSHAL)
      }
      "have a strength" in {
        figure.strength should be (10)
      }
      "have a String for Tui" in {
        figure.stringOut should be ("[10]")
      }
      "have a String representation" in {
        figure.toString should be ("[10]")
      }
      "have a player" in {
        figure.player should be (player)
      }
    }
    "new General" should {
      val player = Player("name", FieldType.B_SIDE)
      val figure = General(player)
      "have a FigureTyp" in {
        figure.figureType should be (FigureType.GENERAL)
      }
      "have a strength" in {
        figure.strength should be (9)
      }
      "have a String for Tui" in {
        figure.stringOut should be ("[09]")
      }
      "have a String representation" in {
        figure.toString should be ("[09]")
      }
      "have a player" in {
        figure.player should be (player)
      }
    }
    "new Colonel" should {
      val player = Player("name", FieldType.B_SIDE)
      val figure = Colonel(player)
      "have a FigureTyp" in {
        figure.figureType should be (FigureType.COLONEL)
      }
      "have a strength" in {
        figure.strength should be (8)
      }
      "have a String for Tui" in {
        figure.stringOut should be ("[08]")
      }
      "have a String representation" in {
        figure.toString should be ("[08]")
      }
      "have a player" in {
        figure.player should be (player)
      }
    }

    "new Major" should {
      val player = Player("name", FieldType.B_SIDE)
      val figure = Major(player)
      "have a FigureTyp" in {
        figure.figureType should be (FigureType.MAJOR)
      }
      "have a strength" in {
        figure.strength should be (7)
      }
      "have a String for Tui" in {
        figure.stringOut should be ("[07]")
      }
      "have a String representation" in {
        figure.toString should be ("[07]")
      }
      "have a player" in {
        figure.player should be (player)
      }
      "new Captain" should {
        val player = Player("name", FieldType.B_SIDE)
        val figure = Captain(player)
        "have a FigureTyp" in {
          figure.figureType should be (FigureType.CAPTAIN)
        }
        "have a strength" in {
          figure.strength should be (6)
        }
        "have a String for Tui" in {
          figure.stringOut should be ("[06]")
        }
        "have a String representation" in {
          figure.toString should be ("[06]")
        }
        "have a player" in {
          figure.player should be (player)
        }
      }
      "new Lieutenant" should {
        val player = Player("name", FieldType.B_SIDE)
        val figure = Lieutenant(player)
        "have a FigureTyp" in {
          figure.figureType should be (FigureType.LIEUTENANT)
        }
        "have a strength" in {
          figure.strength should be (5)
        }
        "have a String for Tui" in {
          figure.stringOut should be ("[05]")
        }
        "have a String representation" in {
          figure.toString should be ("[05]")
        }
        "have a player" in {
          figure.player should be (player)
        }
      }
      "new Seargeant" should {
        val player = Player("name", FieldType.B_SIDE)
        val figure = Sergeant(player)
        "have a FigureTyp" in {
          figure.figureType should be (FigureType.SERGEANT)
        }
        "have a strength" in {
          figure.strength should be (4)
        }
        "have a String for Tui" in {
          figure.stringOut should be ("[04]")
        }
        "have a String representation" in {
          figure.toString should be ("[04]")
        }
        "have a player" in {
          figure.player should be (player)
        }
        "new Miner" should {
          val player = Player("name", FieldType.B_SIDE)
          val figure = Miner(player)
          "have a FigureTyp" in {
            figure.figureType should be (FigureType.MINER)
          }
          "have a strength" in {
            figure.strength should be (3)
          }
          "have a String for Tui" in {
            figure.stringOut should be ("[M3]")
          }
          "have a String representation" in {
            figure.toString should be ("[M3]")
          }
          "have a player" in {
            figure.player should be (player)
          }

          "new Flag" should {
            val player = Player("name", FieldType.B_SIDE)
            val figure = Flag(player)
            "have a FigureTyp" in {
              figure.figureType should be (FigureType.FLAG)
            }
            "have a strength" in {
              figure.strength should be (0)
            }
            "have a String for Tui" in {
              figure.stringOut should be ("[FL]")
            }
            "have a String representation" in {
              figure.toString should be ("[FL]")
            }
            "have a player" in {
              figure.player should be (player)
            }
          }
          "new Spy" should {
            val player = Player("name", FieldType.B_SIDE)
            val figure = Spy(player)
            "have a FigureTyp" in {
              figure.figureType should be (FigureType.SPY)
            }
            "have a strength" in {
              figure.strength should be (1)
            }
            "have a String for Tui" in {
              figure.stringOut should be ("[SP]")
            }
            "have a String representation" in {
              figure.toString should be ("[SP]")
            }
            "have a player" in {
              figure.player should be (player)
            }
          }
          "new Bomb" should {
            val player = Player("name", FieldType.B_SIDE)
            val figure = Bomb(player)
            "have a FigureTyp" in {
              figure.figureType should be (FigureType.BOMB)
            }
            "have a strength" in {
              figure.strength should be (100)
            }
            "have a String for Tui" in {
              figure.stringOut should be ("[BO]")
            }
            "have a String representation" in {
              figure.toString should be ("[BO]")
            }
            "have a player" in {
              figure.player should be (player)
            }
          }
        }

      }
    }
  }
}
