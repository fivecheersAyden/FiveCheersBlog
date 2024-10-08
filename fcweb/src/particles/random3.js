export const random3 = {
    background: {
        color: "#000000"
    },
    particles: {
        color: { value: "#fff" },
        move: {
            direction: "bottom",
            enable: true,
            outModes: "out",
            speed: 1.2
        },
        number: {
            density: {
                enable: true,
                area: 800
            },
            value: 160
        },
        opacity: {
            value: 0.7
        },
        shape: {
            type: "circle"
        },
        size: {
            value: 10
        },
        wobble: {
            enable: true,
            distance: 10,
            speed: 10
        },
        zIndex: {
            value: { min: 0, max: 100 }
        }
    }
}